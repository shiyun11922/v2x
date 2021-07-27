package com.neko.seed.auth.service;

import com.neko.seed.auth.entity.AuthResult;
import com.neko.seed.auth.exception.TokenException;

public interface AuthService {

    AuthResult auth(String subject) throws TokenException;
}
