package com.example.gamecenter.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.Assert;

@Component
public class tokenUtil {
@Autowired
    private redisUtil redisUtil;


public String setToken(String email){
    String randomToken= RandomStringUtils.randomAlphanumeric(8);
    redisUtil.set("token"+email,randomToken,60);
    return randomToken;
}
public void updateToken(String email){
    Object value=redisUtil.get("token"+email);
    Assert.notNull(value,"token鉴权失败");
    redisUtil.expire("token"+email,60);

}

public boolean validate(String email,String token){
    Object value=redisUtil.get("token"+email);
    if(value!=null&&String.valueOf(value).equals(token))return true;
    else return false;
}

public boolean deleteToken(String email){
    Object value = redisUtil.get("token"+email);
    if(value!=null) {
        redisUtil.delKey("token" + email);
        return true;
    }

    else return false;
}



}
