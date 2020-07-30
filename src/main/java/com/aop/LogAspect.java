package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

	@Pointcut("execution(public void com.impl.NovelStorage.*(String)) && args(point))")
	public void LogAspect(String point) { }

	@Around("LogAspect(point)")
	public void doAroundLog(ProceedingJoinPoint pjp, String point) {
		try {
			log.info(point + " 正在更新");
			pjp.proceed();
			log.info(point + " 更新完成");
		} catch (Throwable e) {
			log.error("更新异常",e);
		}
	}
}
