package com.like.pmp.server.service;

import com.like.pmp.model.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysDeptService extends IService<SysDept> {

    List<SysDept> queryAll(Map<String,Object> map);

    List<Long> queryDeptIds(Long parentId);
}
