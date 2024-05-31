package com.ustcsse.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ustcsse.member.model.MemberQueryParams;
import com.ustcsse.member.model.po.Member;
import com.ustcsse.common.model.PageParams;
import com.ustcsse.common.model.PageResult;
import com.ustcsse.member.model.vo.MemberVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ustcsse
 * @since 2024-03-28
 */
public interface MemberService extends IService<Member> {

    PageResult<MemberVo> getAll(PageParams pageParams, MemberQueryParams queryParams);

    Member getByCarplate(String carplate);
}
