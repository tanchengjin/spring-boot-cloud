package com.tanchengjin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://github.com/auth0/java-jwt">...</a>
 */
public class JWTUtil {
    //加密密钥
    private final static String SECRET = "666666";

    /**
     * 生成JWT TOkEN 字符串
     * JWT构成: Header、Payload、Signature
     */
    public static String generateToken(long userId, String username) {

//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.SECOND, 30);
//        Date expiredAt = instance.getTime();
        ZonedDateTime zonedDateTime = LocalDateTime.now().plus(30, ChronoUnit.MINUTES).atZone(ZoneId.systemDefault());
        Date expiredAt = Date.from(zonedDateTime.toInstant());

        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");//签名算法
        header.put("typ", "JWT");//令牌类型


        /**
         * iss (issuer)：签发人
         * exp (expiration time)：过期时间，以秒为单位
         * iat (Issued At)：签发时间
         * nbf (Not Before)：生效时间
         * jti (JWT ID)：JWT 的唯一标识。用来防止 JWT 重复。
         * sub (subject)：主题
         * aud (audience)：接受JWT的一方
         */
        String token = JWT.create().withHeader(header)
                .withClaim("sub", "subject")
                .withClaim("name", username)
                .withClaim("userId", userId)
                .withClaim("iat", new Date())
                .withIssuer("web")
                .withIssuedAt(new Date())
                .withExpiresAt(expiredAt)
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }

    /**
     * 校验token令牌合法性，并返回token中的Payload信息
     *
     * @param token token令牌
     */
    public static Map<String, Claim> verifyTokenAndGetClaim(String token) {
        DecodedJWT jwt = null;
        JWTVerifier build = JWT.require(Algorithm.HMAC256(SECRET)).build();
        jwt = build.verify(token);
        return jwt.getClaims();
    }

    public static void main(String[] args) {
        String s = generateToken(1, "admin");
        System.out.println(s);
//        String token = generateToken();
//        System.out.println(token);
        Map<String, Claim> stringClaimMap = verifyTokenAndGetClaim(s);
        System.out.println(stringClaimMap);
    }

    /**
     * 生成RSA公钥与私钥对
     */
    public static void generateRSAKey() {
        KeyPairGenerator rsa = null;
        try {
            rsa = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        rsa.initialize(2048);
        //生成公钥与密钥对
        KeyPair keyPair = rsa.generateKeyPair();

        //获取公钥与私钥对象
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        System.out.printf("private key: [%s]%n", base64Encoder.encode(privateKey.getEncoded()));
        System.out.printf("public  key: [%s]%n", base64Encoder.encode(publicKey.getEncoded()));
    }

    /**
     * 生成RSA公钥与私钥对
     */
    public static void generateKey() {
        KeyPairGenerator rsa = null;
        try {
            rsa = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        rsa.initialize(2048);
        //生成公钥与密钥对
        KeyPair keyPair = rsa.generateKeyPair();

        //获取公钥与私钥对象
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        System.out.printf("private key: [%s]%n", base64Encoder.encode(privateKey.getEncoded()));
        System.out.printf("public  key: [%s]%n", base64Encoder.encode(publicKey.getEncoded()));
    }

    /**
     * 将本地存储的私钥转换为PrivateKey对象
     *
     * @param privateKey 私钥
     */
    private PrivateKey getPrivateKey(String privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec((new BASE64Decoder()).decodeBuffer(privateKey));
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        return rsa.generatePrivate(pkcs8EncodedKeySpec);
    }
}
