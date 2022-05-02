package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 岗位信息表 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    void deleteBatchById(@Param("params") String params);
}
