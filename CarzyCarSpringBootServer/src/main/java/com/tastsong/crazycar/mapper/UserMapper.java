package com.tastsong.crazycar.mapper;

import com.tastsong.crazycar.model.UserModel;

public interface UserMapper {
    public UserModel getUserByUid(Integer uid);
    public UserModel getUserByName(String userName);
    
}