package com.like.pmp.server.service;

import com.like.pmp.model.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysUserService extends IService<SysUser> {

    boolean updateUserPassword(Long userId,String oldPassword,String newPassword);

}
