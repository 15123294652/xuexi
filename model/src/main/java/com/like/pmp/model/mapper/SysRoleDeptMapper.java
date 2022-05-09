package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysRoleDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {
    List<Long> queryDeptIdList(@Param("roleId") Long roleId);
    void deleteBatch(@Param("roleId") String roleId);
}
