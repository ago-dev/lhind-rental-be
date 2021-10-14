package com.dago.service.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Aspect
@Component
public class LoggingAdvice {
    private static final String LOG_CLASS_METHODS_EXECUTION_STRING =
            "execution(* com.dago..controller..*(..))";

    private final ObjectMapper objMapper;

    @Pointcut(LOG_CLASS_METHODS_EXECUTION_STRING)
    public void logClassMethods() {
    }

    @Around("logClassMethods()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] array = pjp.getArgs();

        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        log.info("METHOD INVOKED { " + className
                + " } : "
                + methodName
                + "() "
                + "Arguments : "
                + objMapper.writeValueAsString(array));

        Object obj = pjp.proceed();

        log.info(className + " : "
                + methodName
                + "() "
                + "Response: "
                + objMapper.writeValueAsString(obj));

        return obj;
    }
}
