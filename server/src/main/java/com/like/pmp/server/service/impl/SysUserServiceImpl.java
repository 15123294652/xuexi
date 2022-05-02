package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.like.pmp.model.entity.SysUser;
import com.like.pmp.model.mapper.SysUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysUserService;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLongHexNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    /*
     * 用户更新密码
     * @author like
     * @date 2022/4/27 17:15
     * @param userId
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public boolean updateUserPassword(Long userId, String oldPassword, String newPassword) {
        SysUser user = new SysUser();
        user.setPassword(newPassword);
        int i = userMapper.update(user, new QueryWrapper<SysUser>().eq("user_id", userId).eq("password", oldPassword));
        if (i>=0){
            return true;
        }
        return false;
    }
}
