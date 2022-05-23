package com.example.gamecenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.gamecenter.bean.user;

import com.example.gamecenter.service.mailService;
import com.example.gamecenter.service.userService;
import com.example.gamecenter.utils.redisUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "用户板块")
@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService;
    @Autowired
    private  mailService mailService;
    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login",method = RequestMethod.GET,params = {"email","password"})
    public ResponseEntity<JSONObject> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password){
       return userService.login(email,password);
    }
    @ApiOperation(value = "注册2（已获得验证码）")
    @RequestMapping(value = "/register",method = RequestMethod.GET,params = {"email","name","password","code"})
    public ResponseEntity<JSONObject> register(@RequestParam(value = "email")String email, @RequestParam(value = "name")String name, @RequestParam(value = "password") String password, @RequestParam(value = "code")String code){
     return userService.register(email,name,password,code);


    }
    @ApiOperation(value = "注册1（申请邮箱验证码）")
    @RequestMapping(value = "/registerMailVerification",method = RequestMethod.GET,params = {"email"})
    public ResponseEntity<JSONObject> emailValidate(@RequestParam(value = "email") String email){
        user user = userService.searchUser(email);
        if(user==null) {
            return mailService.sendMail(email);
        }
        else {
            JSONObject result=new JSONObject();
            result.put("status",HttpStatus.NOT_ACCEPTABLE);
            result.put("msg","email already existed");
            return new ResponseEntity<JSONObject>(result,HttpStatus.NOT_ACCEPTABLE);
        }
    }



    @ApiOperation(value = "上传头像")
    @RequestMapping(value = "/uploadHeadPortrait",method = RequestMethod.POST,headers = {"token","email"})
    public ResponseEntity<JSONObject> uploadHeadPortrait(@RequestHeader(value = "email")String email, @RequestPart(value = "file") MultipartFile img,@RequestHeader(value = "token") String token){
        return userService.uploadHeadPortrait(email,token,img);


    }

    @ApiOperation(value = "用户登出")
    @RequestMapping(value = "/logOut",method = RequestMethod.GET,headers = {"token","email"})
    public ResponseEntity<JSONObject>logOut(@RequestHeader(value = "email")String email,@RequestHeader(value = "token")String token){
        return userService.logOut(email,token);
    }


}
