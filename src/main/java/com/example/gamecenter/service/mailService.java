package com.example.gamecenter.service;

import com.alibaba.fastjson.JSONObject;
import com.example.gamecenter.utils.mailUtil;
import com.example.gamecenter.utils.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class mailService {
    @Autowired
    private com.example.gamecenter.utils.mailUtil mailUtil;
    @Autowired
    private com.example.gamecenter.utils.redisUtil redisUtil;

    public ResponseEntity<JSONObject> sendMail(String email){
        JSONObject result=new JSONObject();
        Object value=redisUtil.get(email);
//        System.out.println(value);
        if(value==null) {
            int randomCode=100000+(int)(Math.random()*(999999-100000+1));
            String code=String.valueOf(randomCode);
            redisUtil.set(email,code,5);
//            String content= "您的验证码为:" + code+"，五分钟内有效，请妥善保存。";


//            mailUtil.sendSimpleMail(email,"欢迎使用LeisureTribe邮箱验证",content);
            mailUtil.sendHtmlMail(email,"欢迎使用LeisureTribe邮箱验证",code);
            result.put("msg","ok");
            result.put("status", HttpStatus.OK);
            return new ResponseEntity<JSONObject>(result,HttpStatus.OK);

        }
        else{
            result.put("msg","the code is already sent");
            result.put("status",HttpStatus.NOT_ACCEPTABLE);
            return new ResponseEntity<JSONObject>(result,HttpStatus.NOT_ACCEPTABLE);
        }

    }

}
