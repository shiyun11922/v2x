package com.neko.seed.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.neko.seed.utils.HttpClientUtil;
import com.neko.seed.auth.entity.AuthResult;
import com.neko.seed.auth.exception.TokenException;
import com.neko.seed.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${traffic.auth.external.url}")
    private String externalUrl;

    @Autowired
    private HttpClientUtil HttpClientUtil;

    @Override
    public AuthResult auth(String token) throws TokenException {

        Objects.requireNonNull(token, "token 不能为空");

        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);

        long start = System.currentTimeMillis();
        String contents = HttpClientUtil.doPost(externalUrl, params, null);

        log.info("auth result ={}, Last={}ms ", contents, System.currentTimeMillis() - start);
        JSONObject result = (JSONObject) JSONObject.parse(contents);
        AuthResult authResult = new AuthResult();

        if (200 == (int) result.get("code")) {
            authResult.setData((String) result.get("data"));
        } else {
            authResult.setMsg((String) result.get("msg"));
            authResult.setUrl((String) result.get("url"));
        }
        authResult.setCode((Integer) result.get("code"));
        return authResult;
    }
}
