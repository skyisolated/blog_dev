package com.zjy.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 通俗理解，AOP就是在一个方法执行前后插入几个方法执行；
 * 执行前执行的叫前置通知，执行后执行的叫后置通知；
 * 切入点就是说哪些类执行前后需要切入；要指定监视的类
 */
@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 该切入点的方法不会执行；监视web包下的所有类的所有方法；
     */
    @Pointcut("execution(* com.zjy.blog.web.*.*(..))")
    public void log(){
        System.out.println("切入点");
    }

    @Before("log()")
    public void before(JoinPoint joinPoint){
//       想办法获取request，必须是ServletRequestAttributes，不然获取不到
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        /**
         * 这里获得的ip是0:0:0:0:0:0:0:1，
         * 这是由于客户端和服务端在同一台电脑所致；
         * 访问时，将localhost改成127.0.0.1即可解决
         */
        String ip = request.getRemoteHost();
        Object[] args = joinPoint.getArgs();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        RequestContent requestContent = new RequestContent(url,ip,classMethod,args);
//        logger.info("Request {}",requestContent);

    }
    @After("log()")
    public void after(){
//        logger.info("------After--------");
    }
    @AfterReturning(returning = "res",pointcut = "log()")
    public void aterReturn(Object res){
//        logger.info("Result {}",res);
    }

    /**
     * 日志需要记录请求的url、ip、调用的方法、参数和返回值等等信息；
     * 将其封装为内部类；不包含返回值，在aterReturn()中处理；
     */
    private class RequestContent{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestContent(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestContent{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }


}
