package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysUserPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户与岗位关联表 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysUserPostMapper extends BaseMapper<SysUserPost> {

    Set<String> getPostNameByUserId(@Param("userId") Long userId);

    List<Long> queryRoleIdList(@Param("userId") Long userId);
}
