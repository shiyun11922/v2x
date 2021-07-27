package com.neko.seed.traffic.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(path = "redirect")
public class RedirectController {

    @Value("${traffic.redirect.host}")
    private String rediectHost;

    private static final Logger LOG = LoggerFactory.getLogger(RedirectController.class);

    @GetMapping(path = "/r1")
    public String r1() {
        return "redirect:http://localhost:8080?token=r1";
    }

    @PostMapping(path = "/traffic")
    public String r(HttpServletRequest request) throws IOException {

        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            LOG.error("请求进入路段信息系统，但是Token为空");
            throw new IOException("请求进入路段信息系统，Token不可为空");
        }

        String[] tt = token.split("\\s+");
        if (tt.length != 2) {
            LOG.error("请求进入路段信息系统，但是Token不符合格式, token={}", token);
            throw new IOException("请求进入路段信息系统，Token不符合格式");
        }

        String auth = tt[1];
        return "redirect:" + rediectHost + "?token=" + auth;
    }

}
