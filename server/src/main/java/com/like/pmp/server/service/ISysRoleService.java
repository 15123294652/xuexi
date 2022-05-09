package com.like.pmp.server.service;

import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.model.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysRoleService extends IService<SysRole> {


    PageUtil queryPage(Map<String, Object> paramMap);

    void savaRole(SysRole entity);

    void updateRole(SysRole entity);

    void deleteBatch(Long[] ids);
}
