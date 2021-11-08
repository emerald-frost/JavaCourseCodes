package com.emeraldfrost.dynamicdatasourcev1.core.aspect;

import com.emeraldfrost.dynamicdatasourcev1.core.annotation.DS;
import com.emeraldfrost.dynamicdatasourcev1.core.config.DatasourceKeyHolder;
import com.emeraldfrost.dynamicdatasourcev1.core.config.SlaveDatasourceRouter;
import com.emeraldfrost.dynamicdatasourcev1.core.constants.Global;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(1)
@RequiredArgsConstructor
@Component
public class DynamicDatasourceAspect {

	private final SlaveDatasourceRouter slaveDatasourceRouter;

	@Pointcut("@annotation(com.emeraldfrost.dynamicdatasourcev1.core.annotation.DS)")
	public void dynamicDatasourcePointCut() {
	}

	@Before("dynamicDatasourcePointCut()")
	public void before(JoinPoint point) {
		final MethodSignature signature = (MethodSignature) point.getSignature();
		DS ds = AnnotationUtils.findAnnotation(signature.getMethod(), DS.class);
		if (ds != null && ds.readOnly()) {
			//走从库
			final String slaveDatasourceKey = slaveDatasourceRouter.route();
			DatasourceKeyHolder.set(slaveDatasourceKey);
			log.debug("method: {}, 走从库", signature.getMethod().getName());
		}
		else {
			//默认走主库
			DatasourceKeyHolder.set(Global.MASTER);
			log.debug("method: {}, 走主库", signature.getMethod().getName());
		}
	}

	@After("dynamicDatasourcePointCut()")
	public void after() {
		DatasourceKeyHolder.remove();
	}
}
