package com.example.gamecenter.service;

import com.example.gamecenter.bean.comment;
import com.example.gamecenter.mapper.commentMapper;
import com.example.gamecenter.utils.tokenUtil;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class commentService {
    @Autowired
    private commentMapper commentMapper;
    @Autowired
    private tokenUtil tokenUtil;

    public ResponseEntity<JSONObject>writeComment(String email,String description,String gameName,int score,String token){
        if(tokenUtil.validate(email,token)){
            tokenUtil.updateToken(email);
            comment searchResult=commentMapper.searchComment(email,gameName);
            if(searchResult==null){
                commentMapper.addComment(email,description,gameName,score);
                JSONObject result=new JSONObject();
                result.put("status", HttpStatus.OK);
                result.put("msg","add a comment");
                return new ResponseEntity<JSONObject>(result,HttpStatus.OK);
            }
            else{
                commentMapper.updateComment(email,description,gameName,score);
                JSONObject result=new JSONObject();
                result.put("status",HttpStatus.OK);
                result.put("msg","update a comment");
                return new ResponseEntity<JSONObject>(result,HttpStatus.OK);

            }
        }
        else{
            JSONObject result=new JSONObject();
            result.put("msg","token authentication failed");
            result.put("status",HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(result,HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<JSONObject>deleteComment(String email,String gameName,String token){
        if(tokenUtil.validate(email,token)){
            tokenUtil.updateToken(email);
            comment searchResult=commentMapper.searchComment(email,gameName);
            if(searchResult!=null) {
                commentMapper.deleteComment(email, gameName);
                JSONObject result = new JSONObject();
                result.put("msg", "ok");
                result.put("status", HttpStatus.OK);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else {
                JSONObject result=new JSONObject();
                result.put("msg","comment not existed");
                result.put("status",HttpStatus.NOT_ACCEPTABLE);
                return new ResponseEntity<>(result,HttpStatus.NOT_ACCEPTABLE);
            }
        }
        else{
            JSONObject result=new JSONObject();
            result.put("msg","token authentication failed");
            result.put("status",HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(result,HttpStatus.UNAUTHORIZED);
        }
    }
}
