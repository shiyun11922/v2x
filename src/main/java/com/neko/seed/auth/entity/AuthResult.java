package com.neko.seed.auth.entity;

import lombok.Data;

@Data
public class AuthResult {

    private int code;
    private String msg;
    private String url;
    private Object data;
}
