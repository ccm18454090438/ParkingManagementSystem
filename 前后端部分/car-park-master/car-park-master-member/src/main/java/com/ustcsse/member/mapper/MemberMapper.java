package com.ustcsse.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ustcsse.member.model.po.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ustcsse
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}
