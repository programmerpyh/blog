package com.ankhnotes.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * 用于jwt token的加解密, 以及相关设置
 */
public class JwtUtil {

    //有效期
    public static final Long JWT_TTL = 72 *60*60*1000L;//72小时

    //密钥明文
    public static final String JWT_KEY = "panyuhao";

    //获取一个UUID, 即唯一标识码
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成加密后的JWT_KEY
     * @return 加密的密钥JWT_KEY
     */
    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);//这里用了decoder而不是encoder
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 对JwtBuilder进行设置, 用于之后生成JWT
     * @param subject token中需要存放的数据(可为json格式数据)
     * @param ttlMillis 有效时间(毫秒)
     * @param uuid 唯一标识码id
     * @return 设置好的JWT构造器
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid){
        //签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        SecretKey secretKey = generalKey();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        ttlMillis = ttlMillis!=null?ttlMillis:JWT_TTL;

        Date expDate = new Date(nowMillis+ttlMillis);


        return Jwts.builder()
                .setId(uuid)  //唯一Id
                .setSubject(subject)  //token中需要存放的数据(可为json格式数据)
                .setIssuer("PanYuHao") //签发者
                .setIssuedAt(now)  //签发时间
                .signWith(signatureAlgorithm, secretKey)  //签发算法和密钥
                .setExpiration(expDate);  //到期时间
    }

    /**
     * 三个createJWT的重载, 满足不同创建JWT的需求
     * @return JWT
     */
    public static String createJWT(String subject){
        return getJwtBuilder(subject, null, getUUID()).compact();
    }

    public static String createJWT(String subject, Long ttlMillis){
        return getJwtBuilder(subject, ttlMillis, getUUID()).compact();
    }

    public static String createJWT(String subject, Long ttlMillis, String uuid){
        return getJwtBuilder(subject, ttlMillis, uuid).compact();
    }

    /**
     * 解析Jwt
     * @param Jwt JWT信息
     * @return 解析body
     */
    public static Claims parseJWT(String Jwt){
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(Jwt).getBody();
    }

    public static void main(String[] args) {
        String token = createJWT("panyuhao");
        System.out.println("token = " + token);
        Claims claims = parseJWT(token);
        System.out.println("claims = " + claims);
    }
}
