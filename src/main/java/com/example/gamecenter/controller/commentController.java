package com.example.gamecenter.controller;

import com.example.gamecenter.service.commentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "评论板块")
@RestController
@RequestMapping("/comment")
public class commentController {
    @Autowired
    private commentService commentService;
    @ApiOperation(value = "发布评论和评分")
    @RequestMapping(value = "/writeComment",method = RequestMethod.GET)
    public ResponseEntity<JSONObject>writeComment(@RequestHeader(value = "email")String email, @RequestHeader(value = "token") String token, @RequestParam(value = "description") String description,@RequestParam(value = "gameName")String gameName,@RequestParam(value = "score")String scoreStr){
        int score=Integer.valueOf(scoreStr);
        return commentService.writeComment(email,description,gameName,score,token);
    }
    @ApiOperation(value = "删除评论和评分")
    @RequestMapping(value = "/deleteComment",method = RequestMethod.GET)
    public ResponseEntity<JSONObject>deleteComment(@RequestHeader(value = "email")String email,@RequestHeader(value = "token")String token,@RequestParam(value = "gameName") String gameName){
        return commentService.deleteComment(email,gameName,token);
    }

    @ApiOperation(value = "查询一个游戏的所有评论")
    @RequestMapping(value = "/searchGameComment",method = RequestMethod.GET)
    public ResponseEntity<JSONObject>searchGameComment(@RequestHeader(value = "email")String email,@RequestHeader(value = "token")String token,@RequestParam(value = "gameName")String gameName){
        return commentService.searchGameComment(email,token,gameName);
    }
}
