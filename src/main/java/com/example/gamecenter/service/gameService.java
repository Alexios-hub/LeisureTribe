package com.example.gamecenter.service;

import com.example.gamecenter.mapper.commentMapper;
import com.example.gamecenter.mapper.gameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class gameService {
    @Autowired
    private gameMapper gameMapper;
    public float getAvarageScore(String gameName){
        return gameMapper.getAverageScore(gameName);
    }

}
