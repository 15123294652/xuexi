package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

     List<Long> queryDeptIds(@Param("parentId") Long parentId);

     Set<Long> queryAllDeptIds();
}
