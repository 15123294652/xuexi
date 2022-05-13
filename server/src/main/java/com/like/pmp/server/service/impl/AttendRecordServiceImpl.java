package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.like.pmp.common.utils.Constant;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.QueryUtil;
import com.like.pmp.model.entity.AttendRecord;
import com.like.pmp.model.mapper.AttendRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.IAttendRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询 ~ 复杂的分页模糊查询 ~ 完全不同于以前的写法
     * @author like
     * @date 2022/5/13 21:15
     * @param params
     * @return com.like.pmp.common.utils.PageUtil
     */
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        IPage<AttendRecord> page=new QueryUtil<AttendRecord>().getQueryPage(params);

        List<AttendRecord> list = baseMapper.queryPage(page,params);
        page.setRecords(list);

        return new PageUtil(page);
    }

    /**
     * 查询所有数据
     * @author like
     * @date 2022/5/13 21:15
     * @param params
     * @return java.util.List<com.like.pmp.model.entity.AttendRecord>
     */
    @Override
    public List<AttendRecord> selectAll(Map<String, Object> params) {
        return baseMapper.selectExportData(params);
    }
    
    /**
     * 中间站转换处理为导出服务
     * @author like
     * @date 2022/5/13 21:15
     * @param list 
     * @return java.util.List<java.util.Map<java.lang.Integer,java.lang.Object>>
     */
    @Override
    public List<Map<Integer, Object>> manageExport(List<AttendRecord> list) {
        List<Map<Integer, Object>> listMap= Lists.newLinkedList();
        //"ID","部门名称","姓名","日期","打卡状态","打卡开始时间","打卡结束时间","工时/小时"

        if (list!=null && !list.isEmpty()){
            list.stream().forEach(entity -> {
                try {
                    Map<Integer,Object> rowMap= Maps.newHashMap();

                    rowMap.put(0,entity.getId());
                   // rowMap.put(1,entity.getDeptName());
                   // rowMap.put(2,entity.getUserName());
                    rowMap.put(3, Constant.DATE_FORMAT.format(entity.getCreateTime()));
                    Constant.AttendStatus status= Constant.AttendStatus.byCode(entity.getStatus().intValue());
                    rowMap.put(4,status!=null?status.getMsg():"空");
                    rowMap.put(5, Constant.DATE_TIME_FORMAT.format(entity.getStartTime()));
                    rowMap.put(6, Constant.DATE_TIME_FORMAT.format(entity.getEndTime()));
                    rowMap.put(7,entity.getTotal());

                    listMap.add(rowMap);
                }catch (Exception e){}
            });
        }

        return listMap;
    }
}
