package com.like.pmp.server.service;

import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.model.entity.AttendRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤记录 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface IAttendRecordService extends IService<AttendRecord> {

    PageUtil queryPage(Map<String, Object> params);

    List<AttendRecord> selectAll(Map<String, Object> params);

    List<Map<Integer, Object>> manageExport(List<AttendRecord> list);
}
