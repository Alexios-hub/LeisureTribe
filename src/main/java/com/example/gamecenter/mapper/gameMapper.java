package com.example.gamecenter.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface gameMapper {
    float getAverageScore(String gameName);
}
