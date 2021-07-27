package com.neko.seed.auth.aop;

import com.neko.seed.auth.entity.AuthResult;
import com.neko.seed.auth.exception.AuthException;
import com.neko.seed.auth.exception.TokenException;
import com.neko.seed.auth.exception.UnauthorizedException;
import com.neko.seed.auth.service.AuthService;
import com.neko.seed.base.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Aspect
@Component
public class AuthRequestAspect {

    //使用org.slf4j.Logger,这是spring实现日志的方法
    private final static Logger logger = LoggerFactory.getLogger(AuthRequestAspect.class);

    @Value("${v2x.skip.auth}")
    private Boolean skipAuth;

    @Autowired
    private AuthService authServiceImpl;

    /**
     * 表示在执行被@MonitorRequest注解修饰的方法之前 会执行doBefore()方法
     *
     * @param joinPoint 连接点，就是被拦截点
     */
    @Before(value = "@annotation(com.neko.seed.auth.annotation.AuthRequest)")
    public void doBefore(JoinPoint joinPoint) throws IOException {
        //获取到请求的属性
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求对象
        HttpServletRequest request = attributes.getRequest();

        Boolean skip = skipAuth || (Boolean) request.getAttribute("skip");

        if (skip == null) {
            skip = Boolean.valueOf(request.getParameter("skip"));
        }
        if (skip != null && skip) return;

        String token = request.getHeader("XX-Access-Token");
        // 判断Token是否为空
        AuthResult auth = null;
        if (token != null && token != "") {
            try {
                auth = authServiceImpl.auth(token);
            } catch (TokenException e) {
                // Token失效，抛出认证异常
                throw new AuthException();
            }
        } else {
            // Token不能为空，抛出未登录异常
            throw new UnauthorizedException();
        }

        if (200 == (auth.getCode())) {
            logger.debug("auth passed");
            return;
        }

        throw new ServiceException(auth, "校验权限失败");
    }
}
