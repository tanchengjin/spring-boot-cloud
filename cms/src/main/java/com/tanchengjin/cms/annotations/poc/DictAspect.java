package com.tanchengjin.cms.annotations.poc;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 实现自定义注解切面
 */
@Aspect
@Component
@Configuration
public class DictAspect {
	//定义切点
	@Pointcut("@annotation(com.tanchengjin.cms.annotations.dict.Dict)")
	private void cut() {
	}


	@Around("@annotation(com.tanchengjin.cms.annotations.dict.Dict)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		// get attribute through annotation
//		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
//		RedisLock redisLock = method.getAnnotation(RedisLock.class);
//		String key = redisLock.value();
//		if (StringUtils.isEmpty(key)) {
//			Object[] args = pjp.getArgs();
//			key = Arrays.toString(args);
//		}
//
//		// do lock
//		boolean lock = distributedLock.lock(key, redisLock.expireMills(), redisLock.retryTimes(),
//			redisLock.retryDurationMills());
//		if (!lock) {
//			log.debug("get lock failed, key: {}", key);
//			return null;
//		}
//
//		// execute method, and unlock
//		log.debug("get lock success, key: {}", key);
//		try {
//			// execute
//			return pjp.proceed();
//		} catch (Exception e) {
//			log.error("execute locked method occurred an exception", e);
//		} finally {
//			// unlock
//			boolean releaseResult = distributedLock.unlock(key);
//			log.debug("release lock: {}, success: {}", key, releaseResult);
//		}

		return null;
	}

	@Before("cut()")
	public void before() {
		System.out.println("自定义注解前置通知！");
	}

	@After("cut()")
	public void after(JoinPoint joinPoint) {
//		joinPoint.getTarget().getClass().getFields()
		System.out.println("自定义注解后置通知！");
	}
}
