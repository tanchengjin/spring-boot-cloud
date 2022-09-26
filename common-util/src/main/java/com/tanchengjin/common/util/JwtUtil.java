package com.tanchengjin.common.util;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author TanChengjin
 * @version v1.0.0
 * @email 18865477815@163.com
 */
public class JwtUtil {
    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final static String secret = "fgjalsgjpagqtjp1759182571238thahjgkahgaGHKSHGOSDI4924GKSDJGKL";

    public static String generateToken() {

        ZonedDateTime zonedDateTime = LocalDateTime.now().plus(30, ChronoUnit.MINUTES).atZone(ZoneId.systemDefault());
        Date expiredDateTime = Date.from(zonedDateTime.toInstant());
        String token = Jwts.builder().claim("uid", "123456").setSubject("subject")
                .setExpiration(expiredDateTime)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return token;
    }

    public static Claims parseToken(String token) {
        final Claims body;
        try {
            body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return body;
    }

    public static boolean checkToken(String token) {
        final Claims body;
        try {
            body = Jwts.parser().setSigningKey("123123123").parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            logger.info(e.getMessage());
            return false;
        }

        if (body.getExpiration() != null) {
            //判断token是否过期
            if (body.getExpiration().before(Calendar.getInstance().getTime())) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String token = Jwts.builder().setId(UUID.randomUUID().toString()).claim("uid", "123456").setSubject("subject")
                .signWith(SignatureAlgorithm.HS256, "123123123")
                .compact();

        System.out.printf("token %s", token);

        final Claims body;
        try {
            body = Jwts.parser().setSigningKey("123123123").parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("\nresult is: " + body.getId());

    }
}
