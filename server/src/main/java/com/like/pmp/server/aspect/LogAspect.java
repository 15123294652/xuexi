package com.like.pmp.server.aspect;

import com.like.pmp.common.utils.HttpContextUtils;
import com.like.pmp.common.utils.IPUtil;
import com.like.pmp.model.entity.SysLog;
import com.like.pmp.server.annotation.LogAnnotation;
import com.like.pmp.server.service.ISysLogService;
import com.like.pmp.server.shiro.ShiroUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import java.lang.reflect.Method;

/**
 * @author like
 * @date 2022年05月13日 19:51
 * 切面处理类
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private ISysLogService logService;
    /**
     * 切入点
     * @author like
     * @date 2022/5/13 19:58
     */
    @Pointcut("@annotation(com.like.pmp.server.annotation.LogAnnotation)")
    public void logPointCut(){

    }

    /**
     * 环绕通知
     * @author like
     * @date 2022/5/13 19:58
     * @param point
     * @return java.lang.Object
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        long start=System.currentTimeMillis();

        Object result=point.proceed();

        long time=System.currentTimeMillis() - start;

        saveLog(point,time);

        return result;
    }

    /**
     * 保存日志
     * @author like
     * @date 2022/5/13 19:57
     * @param point
     */
    private void saveLog(ProceedingJoinPoint point,Long time){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        //获取请求参数的描述信息
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        if (annotation != null){
            sysLog.setOperation(annotation.value());
        }
        //获取操作的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(new StringBuilder(className).append(".").append(methodName).append("()").toString());

        //获取请求参数
        Object[] args=point.getArgs();
        String params=new Gson().toJson(args[0]);
        sysLog.setParams(params);

        //获取ip
        sysLog.setIp(IPUtil.getIpAddr(HttpContextUtils.getHttpServletRequest()));

        //获取剩下的参数
        sysLog.setCreateDate(DateTime.now().toDate());
        String userName= ShiroUtil.getUserEntity().getUsername();
        sysLog.setUsername(userName);

        //执行时间
        sysLog.setTime(time);
        logService.save(sysLog);

    }
}
