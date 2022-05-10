package com.like.pmp.server.service;

import com.like.pmp.model.entity.SysUserPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户与岗位关联表 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysUserPostService extends IService<SysUserPost> {

    String  getPostNameByUserId(Long userId);

    void postSaveOrUpdate(Long userId, List<Long> postIds);

    List<Long> queryRoleIdList(Long userId);
}
