package com.like.pmp.server.service.impl;

import com.like.pmp.model.entity.AttendRecord;
import com.like.pmp.model.mapper.AttendRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.IAttendRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考勤记录 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class AttendRecordServiceImpl extends ServiceImpl<AttendRecordMapper, AttendRecord> implements IAttendRecordService {

}
