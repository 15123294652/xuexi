package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysUserMapper extends BaseMapper<SysUser> {



    List<String> queryAllPerms(Long userId);
}
