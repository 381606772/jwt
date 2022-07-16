package com.fujian.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final String SIGN = "!Q@W#e$R";

    /**
     * 生成token
     */
    public static String getToken(Map<String, String> map) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        Map<String, Object> header = new HashMap<>();


        JWTCreator.Builder builder = JWT.create();
        builder.withHeader(header);

        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        // 生成令牌
        String token = builder
                        .withExpiresAt(calendar.getTime())  // token expire time
                        .sign(Algorithm.HMAC256(SIGN)); // signature
        return token;
    }

    /**
     * 验证token
     */
    public static void verifyToken(String token) {
        // verify Json Web Token
        JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        return verify;
    }
}
