package atlan.ceer.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("tokenUtil")
public class TokenUtil {
    private static final long EXPIRE_TIME = 20 * 24 * 60 * 60 * 1000;//二十天过期
    private static final long MESSAGE_TIME = 6 * 60 * 1000;//6分钟
    private static final String TOKEN_SECRET = "atlan";

    //创建token登录使用
    public String createToken(String username,String id){
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            String result= JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("id", id)
                    .withExpiresAt(date)
                    .sign(algorithm);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //验证token
    public boolean verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //获取token里面的信息
    public Map parseToken(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String,String> map=new HashMap<>(2);
            map.put("id",jwt.getClaim("id").asString());
            map.put("username",jwt.getClaim("username").asString());
            return map;
        } catch (JWTDecodeException e){
            e.printStackTrace();
            return null;
        }
    }

    public String createMessageToken(String code){
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + MESSAGE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            String result= JWT.create()
                    .withHeader(header)
                    .withClaim("code", code)
                    .withExpiresAt(date)
                    .sign(algorithm);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取token里面的验证码
    public String parseCodeToken(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("code").asString();
        } catch (JWTDecodeException e){
            e.printStackTrace();
            return null;
        }
    }

}
