package com.like.pmp.server.service;

import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.model.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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

    PageUtil queryPage(Map<String, Object> paramMap);

    void saveUser(SysUser entity);

    SysUser getInfo(Long userId);

    void updateUser(SysUser user);

    void deleteUser(Long[] ids);

    void updatePsd(Long[] ids);
}
