package com.example.gamecenter.mapper;

import com.example.gamecenter.bean.comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface commentMapper {
    void addComment(String email,String description,String gameName,int score);
    comment searchComment(String email, String gameName);
    void updateComment(String email,String description,String gameName,int score);
    void deleteComment(String email,String gameName);

}
