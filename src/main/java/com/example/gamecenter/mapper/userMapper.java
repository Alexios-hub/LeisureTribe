package com.example.gamecenter.mapper;

import com.example.gamecenter.bean.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.jni.User;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface userMapper {
    void register(String email,String name,String password);
    user login(String email,String password);
    user searchUser(String email);
    void updateHeadPortrait(String email,String headPortrait);
}
