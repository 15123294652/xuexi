package com.like.pmp.server.service;

import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.model.entity.SysPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 岗位信息表 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysPostService extends IService<SysPost> {

    PageUtil queryPage(Map<String, Object> params);

    void savePost(SysPost post);

    void updatePost(SysPost post);

    void deletepath(Long[] ids);
}
