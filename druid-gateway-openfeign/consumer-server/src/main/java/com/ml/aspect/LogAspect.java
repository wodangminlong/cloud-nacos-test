package com.ml.aspect;

import com.alibaba.fastjson.JSON;
import com.ml.model.SystemLog;
import com.ml.openfeign.TestFeignClient;
import com.ml.util.IpUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author dml
 * @date 2021/12/7 9:26
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    private final TestFeignClient testFeignClient;

    public LogAspect(TestFeignClient testFeignClient) {
        this.testFeignClient = testFeignClient;
    }

    @Pointcut("@annotation(Log)")
    public void logPointcut() {
        // do something
    }

    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = pjp.proceed();
        SystemLog systemLog = getSystemLog(pjp, "");
        currentTime.remove();
        testFeignClient.addSystemLog(systemLog);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        ProceedingJoinPoint pjp = (ProceedingJoinPoint)joinPoint;
        SystemLog systemLog = getSystemLog(pjp, e.getMessage());
        currentTime.remove();
        testFeignClient.addSystemLog(systemLog);
    }

    private SystemLog getSystemLog(ProceedingJoinPoint pjp, String e) {
        SystemLog systemLog = new SystemLog();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Log l = method.getAnnotation(Log.class);
        HttpServletRequest request = getHttpServletRequest();
        String ip = IpUtils.getIpAddress(request);
        String address = getAddress(ip);
        String browser = getBrowser(request);
        String systemName = getSystemName(request);
        systemLog.setRemark(l.value())
                .setLogType(getLogLevel())
                .setMethod(pjp.getTarget().getClass().getName()+"."+signature.getName())
                .setParams(JSON.toJSONString(pjp.getArgs()))
                .setRequestIp(ip)
                .setDuration(System.currentTimeMillis() - currentTime.get())
                .setUsername("admin")
                .setAddress(address)
                .setBrowser(browser)
                .setSystemName(systemName)
                .setError(e)
                .setCreateTime(LocalDateTime.now());
        return systemLog;
    }

    private HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    private String getLogLevel() {
        if (log.isDebugEnabled()) {
            return "DEBUG";
        } else if (log.isInfoEnabled()) {
            return "INFO";
        } else if (log.isWarnEnabled()) {
            return "WARN";
        } else if (log.isErrorEnabled()) {
            return "ERROR";
        }
        return "";
    }

    private String getAddress(String ip) {
        String api = String.format("https://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true", ip);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForEntity(api, String.class).getBody();
        return JSON.parseObject(result).getString("addr");
    }

    private String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    private String getSystemName(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        return operatingSystem.getName();
    }

}
