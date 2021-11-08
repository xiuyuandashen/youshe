package com.youshe.mcp.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/09/18/15:07
 * @Description:
 */
@Aspect
@Component
public class AspectTest {

    @Pointcut("execution(* com.youshe.mcp.controller.testController.*(..))")
    public void pointcut(){
    }


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            System.out.println("around");
            Signature signature = joinPoint.getSignature();
            Object[] args = joinPoint.getArgs();
            String a = (String) args[0];
            //System.out.println(signature);

            System.out.println(a);
            return joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw  e;
        } finally {
            System.out.println("around");

        }
    }
}
