package com.example.gamecenter.service;

import com.alibaba.fastjson.JSONObject;
import com.example.gamecenter.bean.user;
import com.example.gamecenter.mapper.userMapper;
import com.example.gamecenter.utils.ossUtil;
import com.example.gamecenter.utils.redisUtil;
import com.example.gamecenter.utils.tokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class userService {
    @Autowired
    private userMapper userMapper;
    @Autowired
    private  redisUtil redisUtil;
    @Autowired
    private tokenUtil tokenUtil;
    @Autowired
    private ossUtil ossUtil;
    public ResponseEntity<JSONObject> login(String email, String password){
        user result = userMapper.login(email,password);
        if(result!=null){
            String token = tokenUtil.setToken(email);
            JSONObject response=new JSONObject();
            response.put("msg","ok");
            response.put("token",token);
            response.put("status",HttpStatus.OK);
            return new ResponseEntity<JSONObject>(response, HttpStatus.OK);
        }
        else{
            JSONObject response =new JSONObject();
            response.put("msg","email not existed or wrong answer");
            response.put("status",HttpStatus.NOT_ACCEPTABLE);
            return new ResponseEntity<JSONObject>(response,HttpStatus.NOT_ACCEPTABLE);
        }
    }
    public user searchUser(String email){

        return userMapper.searchUser(email);
    }
    public ResponseEntity<JSONObject> register (String email,String name,String password,String code){
        user user =  searchUser(email);
        if(user==null) {
            JSONObject result = new JSONObject();
            Object value = redisUtil.get(email);
            if (value == null || !String.valueOf(value).equals(code)) {
                result.put("status", HttpStatus.NOT_ACCEPTABLE);
                result.put("msg", "wrong code");
                return new ResponseEntity<JSONObject>(result,HttpStatus.NOT_ACCEPTABLE);
            }
            else if(String.valueOf(value).equals(code)){
                result.put("status",HttpStatus.OK);
                result.put("msg","ok");
                userMapper.register(email,name,password);
                return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
            }
            else {
                result.put("status",HttpStatus.NOT_ACCEPTABLE);
                result.put("msg","unknown error");

                return new ResponseEntity<JSONObject>(result,HttpStatus.NOT_ACCEPTABLE);
            }
        }
        else{
            JSONObject result=new JSONObject();
            result.put("status",HttpStatus.NOT_ACCEPTABLE);
            result.put("msg","email already existed");
            return new ResponseEntity<JSONObject>(result,HttpStatus.NOT_ACCEPTABLE);
        }

    }

    public ResponseEntity<JSONObject> uploadHeadPortrait(String email, String token, MultipartFile img){
        if(tokenUtil.validate(email,token)){
            tokenUtil.updateToken(email);
            JSONObject uploadResult=ossUtil.upload(img,"head portrait",email);
            if((Integer) uploadResult.get("status")==200){
                String URL=String.valueOf(uploadResult.get("URL"));
                userMapper.updateHeadPortrait(email,URL);

                return new ResponseEntity<JSONObject>(uploadResult,HttpStatus.OK);
            }
            else return new ResponseEntity<JSONObject>(uploadResult,HttpStatus.INTERNAL_SERVER_ERROR);



        }
        else{
            JSONObject result=new JSONObject();
            result.put("status",HttpStatus.UNAUTHORIZED);
            result.put("msg","token authentication failed");
            return new ResponseEntity<JSONObject>(result,HttpStatus.UNAUTHORIZED);
        }
    }


    public ResponseEntity<JSONObject>logOut(String email,String token){
        if(tokenUtil.validate(email,token)){
            if(tokenUtil.deleteToken(email)){
                JSONObject result=new JSONObject();
                result.put("msg","ok");
                result.put("status",HttpStatus.OK);
                return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
            }
            else {
                JSONObject result =new JSONObject();
                result.put("msg","invalid token");
                result.put("status",HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<JSONObject>(result,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            JSONObject result=new JSONObject();
            result.put("msg","token authentication failed");
            result.put("status",HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<JSONObject>(result,HttpStatus.UNAUTHORIZED);
        }
    }


}
