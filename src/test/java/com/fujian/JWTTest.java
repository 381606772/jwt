package com.fujian;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTTest {

    @Test
    public void testGenerateJsonWebToken() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 100);

        Map<String, Object> header = new HashMap<>();
        // 生成令牌
        String token = JWT.create()
                        .withHeader(header) // header
                        .withClaim("userId", 123)  // payload
                        .withClaim("userName", "xiaoming")
                        .withExpiresAt(calendar.getTime())  // token expire time
                        .sign(Algorithm.HMAC256("!Q@W#e$R")); // signature

        System.out.println("token = " + token);

        // output
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6InhpYW9taW5nIiwiZXhwIjoxNjU3OTUyNDM0LCJ1c2VySWQiOjEyM30.IBdZhiX1X2_G6QS0AGSjcGQqi7sv-m3cSY8hFr1tlI0
    }

    @Test
    public void testVerifyJsonWebToken() {
        // create Json Web Token Verify Object.
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!Q@W#e$R")).build();

        // verify success, then get Json Web Token Object.
        DecodedJWT decodedJWT = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6InhpYW9taW5nIiwiZXhwIjoxNjU3OTUyNDM0LCJ1c2VySWQiOjEyM30.IBdZhiX1X2_G6QS0AGSjcGQqi7sv-m3cSY8hFr1tlI0");
        System.out.println(decodedJWT.getClaims().get("userId").asInt());
        System.out.println(decodedJWT.getClaims().get("userName").asString());
    }
}
