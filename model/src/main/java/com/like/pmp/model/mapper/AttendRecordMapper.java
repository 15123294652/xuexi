package com.like.pmp.model.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.pmp.model.entity.AttendRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤记录 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface AttendRecordMapper extends BaseMapper<AttendRecord> {

    List<AttendRecord> queryPage(@Param("page") IPage<AttendRecord> page, @Param("paramMap") Map<String, Object> params);

    List<AttendRecord> selectExportData(@Param("params")Map<String, Object> params);

    List<AttendRecord> queryPageSqlServer(@Param("params")Map<String, Object> params);
}
