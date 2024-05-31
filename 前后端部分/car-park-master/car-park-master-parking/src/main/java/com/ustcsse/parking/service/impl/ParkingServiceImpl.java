package com.ustcsse.parking.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.common.model.R;
import com.ustcsse.common.utils.IdWorkerUtils;
import com.ustcsse.parking.config.PayNotifyConfig;
import com.ustcsse.parking.feign.MemberFeignService;
import com.ustcsse.parking.feign.OrderFeignService;
import com.ustcsse.parking.feign.ParkinglotsFeignService;
import com.ustcsse.parking.mapper.ParkingMapper;
import com.ustcsse.parking.model.ParkingQueryParams;
import com.ustcsse.parking.model.po.Member;
import com.ustcsse.parking.model.po.Order;
import com.ustcsse.parking.model.po.Parking;
import com.ustcsse.parking.model.po.Parkinglot;
import com.ustcsse.parking.model.vo.ParkingVo;
import com.ustcsse.parking.service.ParkingService;
import com.ustcsse.parking.service.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.util.concurrent.Queues;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ustcsse
 */
@Slf4j
@Service
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {

    @Autowired
    ParkingMapper parkingMapper;

    @Autowired
    ParkinglotsFeignService parkinglotsFeignService;

    @Autowired
    OrderFeignService orderFeignService;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    WebSocketServer webSocketServer;

    @Autowired
    StringRedisTemplate redisTemplate;


//    @RabbitListener(queues = PayNotifyConfig.PAYNOTIFY_QUEUE)
//    public void receiveMessage(String message) {
//        String trade = message.substring(1, message.length() + 1);
//        Order order = JSONObject.parseObject(trade, Order.class);
//        Integer parkingid = order.getParkingid();
//        Integer parkinglotid = order.getParkinglotid();
//        String carplate = order.getCarplate();
//        if(message.charAt(0) == '1'){
//            //TODO:放行
//            webSocketServer.sendOneMessage("1", "【新消息】临时停车：" + carplate + ",支付成功，谢谢光临！");
//        }else{
//            webSocketServer.sendOneMessage("1", "【新消息】支付失败，请联系管理人员！");
//        }
//    }

    @Override
    public PageResult<Parking> getAll(PageParams pageParams, ParkingQueryParams queryParams) {
        LambdaQueryWrapper<Parking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(queryParams.getCarplate()), Parking::getCarplate, queryParams.getCarplate());
        if(queryParams.getParkinglotName() != null && !queryParams.getParkinglotName().equals("")){
            queryWrapper.eq(Parking::getParkinglotName, queryParams.getParkinglotName());
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(queryParams.getDateRange() != null && queryParams.getDateRange().length > 0){
            if(queryParams.getDateRange()[0] != null && !queryParams.getDateRange()[0].equals("")){
                LocalDateTime begin = LocalDateTime.parse(queryParams.getDateRange()[0], format);
                queryWrapper.ge(Parking::getIntime, begin);
            }
            if(queryParams.getDateRange()[1] != null && !queryParams.getDateRange()[1].equals("")){
                LocalDateTime end = LocalDateTime.parse(queryParams.getDateRange()[1], format);
                queryWrapper.le(Parking::getIntime, end);
            }
        }

        if(queryParams.getDateRange2() != null && queryParams.getDateRange2().length > 0){
            if(queryParams.getDateRange2()[0] != null && !queryParams.getDateRange2()[0].equals("")){
                LocalDateTime begin = LocalDateTime.parse(queryParams.getDateRange2()[0], format);
                queryWrapper.ge(Parking::getIntime, begin);
            }
            if(queryParams.getDateRange2()[1] != null && !queryParams.getDateRange2()[1].equals("")){
                LocalDateTime end = LocalDateTime.parse(queryParams.getDateRange()[1], format);
                queryWrapper.le(Parking::getIntime, end);
            }
        }
        Page<Parking> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Parking> result = parkingMapper.selectPage(page, queryWrapper);
        List<Parking> items = result.getRecords();
        int total = (int)result.getTotal();
        PageResult<Parking> pageResult = new PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
        return pageResult;
    }

    @Override
    public void add(ParkingVo parkingVo) {
        Parking parking = new Parking();
        parking.setCarplate(parkingVo.getCarplate());
        parking.setIntime(parkingVo.getInTime());
        parking.setParkinglotid(parkingVo.getParkinglotid());
        parking.setParkinglotName(parkingVo.getParkinglotName());

        //自定义一个大日期先作为出厂默认时间（以免查询功能报错）
        String str = "2099-12-31 23:59:59";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime defaultOutTime = LocalDateTime.parse(str, format);
        parking.setOuttime(defaultOutTime);

        Member member = memberFeignService.getByCarplate(parkingVo.getCarplate());
        if(member != null){
            parking.setMemberid(member.getId());
        }
        parking.setStatus(0);
        this.save(parking);
    }

    @Override
    public void update(ParkingVo parkingVo) {
        LambdaQueryWrapper<Parking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Parking::getCarplate, parkingVo.getCarplate());
        queryWrapper.eq(Parking::getParkinglotName, parkingVo.getParkinglotName());
        String str = "2099-12-31 23:59:59";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime defaultOutTime = LocalDateTime.parse(str, format);
        queryWrapper.eq(Parking::getOuttime, defaultOutTime);
        Parking parking = this.getOne(queryWrapper);
        if(parking != null) {
            if(parkingVo.getOutTime() != null){
                parking.setOuttime(parkingVo.getOutTime());
            }else{
                parking.setIntime(parkingVo.getInTime());
            }
            parking.setStatus(1);
            this.updateById(parking);
        }
        return;
    }

    @Override
    public PageResult<Parking> getParkingCar(PageParams pageParams, ParkingQueryParams queryParams) {
        LambdaQueryWrapper<Parking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(queryParams.getCarplate()), Parking::getCarplate, queryParams.getCarplate());
        if(queryParams.getParkinglotName() != null && !queryParams.getParkinglotName().equals("")){
            queryWrapper.eq(Parking::getParkinglotName, queryParams.getParkinglotName());
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(queryParams.getDateRange() != null && queryParams.getDateRange().length > 0){
            if(queryParams.getDateRange()[0] != null && !queryParams.getDateRange()[0].equals("")){
                LocalDateTime begin = LocalDateTime.parse(queryParams.getDateRange()[0], format);
                queryWrapper.ge(Parking::getIntime, begin);
            }
            if(queryParams.getDateRange()[1] != null && !queryParams.getDateRange()[1].equals("")){
                LocalDateTime end = LocalDateTime.parse(queryParams.getDateRange()[1], format);
                queryWrapper.le(Parking::getIntime, end);
            }
        }
        LocalDateTime defultOutTime = LocalDateTime.parse("2099-12-31 23:59:59", format);
        queryWrapper.eq(Parking::getOuttime, defultOutTime);
        queryWrapper.eq(Parking::getStatus, 1);
        Page<Parking> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Parking> result = parkingMapper.selectPage(page, queryWrapper);
        List<Parking> items = result.getRecords();
        int total = (int)result.getTotal();
        PageResult<Parking> pageResult = new PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
        return pageResult;
    }

    @Override
    public R parkingIn(ParkingVo parkingVo) {
        Parking parking = getByParkingVo(parkingVo);
        if(parking != null){
            update(parkingVo);
            webSocketServer.sendOneMessage("1","[WELCOME]" + parkingVo.getCarplate() + ",欢迎进入停车场!");
            return R.ok("入库成功！");
        }
        //查询停车场是否有余位
        Parkinglot parkinglot = parkinglotsFeignService.getByName(parkingVo.getParkinglotName());
        if(parkinglot.getLeftAmount() == 0){
            webSocketServer.sendOneMessage("1","[SORRY] 车位不足，请您等候！");
            return R.error(-1, "车位不足，请等候！");
        }
        parkinglot.setLeftAmount(parkinglot.getLeftAmount() - 1);
        parkinglot.setCarAmount(parkinglot.getCarAmount() + 1);

        //更新停车场信息
        parkinglotsFeignService.addParkinglot(parkinglot);

        //添加停车记录
        parkingVo.setParkinglotid(parkinglot.getId());
        add(parkingVo);
        parkinglotsFeignService.addParkinglot(parkinglot);
        webSocketServer.sendOneMessage("1","[WELCOME]" + parkingVo.getCarplate() + ",欢迎进入停车场!");
        return R.ok("欢迎光临！");
    }

    @Override
    public R parkingOut(ParkingVo parkingVo) {
        //查询会员信息
        Member member = memberFeignService.getByCarplate(parkingVo.getCarplate());
        //查询停车场
        Parkinglot parkinglot = parkinglotsFeignService.getByName(parkingVo.getParkinglotName());
        //查询停车记录
        Parking parking = getByParkingVo(parkingVo);

        //计费
        LocalDateTime intime = parking.getIntime();
        LocalDateTime outTime = parkingVo.getOutTime();
        double charging = charging(intime, outTime);
        //计算会员是否在有效期内
        long diff = member == null ? 0 : ChronoUnit.DAYS.between(LocalDateTime.now().toLocalDate(), member.getBasedate());;

        //生成初始订单
        Order order = new Order();
        order.setAmount(charging);
        order.setMethod(1);
        order.setType(0);
        if(member != null && diff >= 0){
            order.setMethod(3);
            order.setMemberid(member.getId());
            order.setType(member.getType());
            order.setAmount(0.0);
        }
        order.setCarplate(parkingVo.getCarplate());
        order.setParkinglotid(parkingVo.getParkinglotid());
        order.setDuration(intime.until(outTime, ChronoUnit.SECONDS));
        order.setTime(LocalDateTime.now());
        order.setParkinglotid(parking.getParkinglotid());
        order.setParkinglotName(parking.getParkinglotName());
        order.setParkingid(parking.getId());
        order.setStatus(0);

        int flag = 0;
        long tradeId = IdWorkerUtils.getInstance().nextId();
        order.setTradeNo(tradeId);
        // 向前端界面发送消息提示
        if((diff < 0 && charging > 0) || (member == null && charging > 0)) {
            if(member == null){
                webSocketServer.sendOneMessage("1", "[NOTICE] 临时停车：" + parkingVo.getCarplate() + "，请支付" + charging + "元！");
            }else{
                webSocketServer.sendOneMessage("1", "[NOTICE] 会员停车：" + parkingVo.getCarplate() + "会员已到期，请支付" + charging + "元！");
            }

            String payCode = orderFeignService.addOrderToPay(order);
//            // 创建一个定时任务，每100毫秒执行一次查询
//            Timer timer = new Timer();
//            // 创建一个定时任务，每100毫秒执行一次查询
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    // 执行查询操作
//                    String payCode = redisTemplate.boundValueOps("order:" + tradeId).get();
//                    // 如果有查询结果，取消定时任务
//                    if (payCode != null) {
//                        timer.cancel();
//                    }
//                }
//            }, 0, 100);
//            String payCode  = redisTemplate.boundValueOps("order:" + tradeId).get();
//            log.info("从redis中获取到二维码：" + payCode);
            //redisTemplate.delete("order:" + tradeId);
            webSocketServer.sendOneMessage("1", payCode);

        }else if(diff > 0){
            webSocketServer.sendOneMessage("1","[NOTICE] 会员停车：" + parkingVo.getCarplate() + ",剩余有效天数："+ diff + "天！");
            order.setStatus(1);
            R res = orderFeignService.addOrder(order);
            if(res.getCode() == 0){
                log.info("订单保存成功!");
            }else{
                log.info("订单保存异常");
            }
            flag = 1;
        } else{
            webSocketServer.sendOneMessage("1","[GOODBYE] 临时停车：" + parkingVo.getCarplate() + ",免费时段,欢迎驶出！" );
            order.setStatus(1);
            R res = orderFeignService.addOrder(order);
            if(res.getCode() == 0){
                log.info("订单保存成功!");
            }else{
                log.info("订单保存异常");
            }
            flag = 1;
        }

        //TODO:放行

        //更新停车场信息
        parkinglot.setLeftAmount(parkinglot.getLeftAmount() + 1);
        parkinglot.setCarAmount(parkinglot.getCarAmount() - 1);
        parkinglotsFeignService.addParkinglot(parkinglot);
        //webSocketServer.sendOneMessage("1", "[SYSTEM] REFLUSH THE PAGE");

        //更新停车记录
        parkingVo.setParkinglotid(parkinglot.getId());
        update(parkingVo);
        if(flag == 1){
            webSocketServer.sendOneMessage("1","[SYSTEM] REFLUSH DISPLAY" );
        }
        return R.ok(tradeId);
    }

    @Override
    public Parking getByParkingVo(ParkingVo parkingVo) {
        LambdaQueryWrapper<Parking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Parking::getCarplate, parkingVo.getCarplate());
        queryWrapper.eq(Parking::getParkinglotName, parkingVo.getParkinglotName());
        String str = "2099-12-31 23:59:59";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime defaultOutTime = LocalDateTime.parse(str, format);
        queryWrapper.eq(Parking::getOuttime, defaultOutTime);
        Parking parking = this.getOne(queryWrapper);
        return parking;
    }

    public double charging(LocalDateTime intime, LocalDateTime outTime){
        // 计算停车时长
        long minutes = intime.until(outTime, ChronoUnit.MINUTES);
        long hours = intime.until(outTime, ChronoUnit.HOURS);
        // 如果停车时间在半小时内，免费
        if (minutes <= 30) {
            return 0.0;
        }
        if(minutes % 60 != 0) hours += 1;
        // 节假日免费
        if (isHoliday(outTime)) {
            return 0.0;
        }
        // 假设节假日判断逻辑为isHoliday()方法

        // 计算停车费用
        double fee = hours * 5.0;
        return fee;
    }

    boolean isHoliday(LocalDateTime dateTime) {
        Month  month = dateTime.getMonth();
        int day = dateTime.getDayOfMonth();

        // 判断是否为春节（农历正月初一）
//        if (month == Month.JANUARY && day == 1) {
//            return true;
//        }

        // 判断是否为五一劳动节（五月1号到5号）
        if (month == Month.MAY && day >= 1 && day <= 5) {
            return true;
        }

        // 判断是否为国庆节（十月1号到7号）
        if (month == Month.OCTOBER && day >= 1 && day <= 7) {
            return true;
        }

        return false;
    }
}
