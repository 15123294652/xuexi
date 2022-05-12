package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


    //查询用户所有的权限
    List<String> queryAllPerms(Long userId);

    //根据当前登录用户id获取部门数据id列表
    Set<Long> queryDeptIdsByUserId(Long userId);

    //查询用户的所有权限
    List<Long> queryAllMenuId(Long userId);


    SysUser selectByUserName(@Param("userName") String userName);

}
