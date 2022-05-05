package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
     List<SysDept> queryAll(Map<String,Object> map);

     List<Long> queryDeptIds(Long parentId);
}
