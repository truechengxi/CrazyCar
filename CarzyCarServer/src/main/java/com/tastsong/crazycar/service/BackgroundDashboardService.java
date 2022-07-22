package com.tastsong.crazycar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tastsong.crazycar.mapper.AvatarMapper;
import com.tastsong.crazycar.mapper.EquipMapper;
import com.tastsong.crazycar.mapper.TimeTrialMapper;
import com.tastsong.crazycar.mapper.UserMapper;

@Service
public class BackgroundDashboardService {
    @Autowired
    private UserMapper userMapper;

    @Autowired 
    private EquipMapper equipMapper;

    @Autowired 
    private AvatarMapper avatarMapper;

    @Autowired
    private TimeTrialMapper timeTrialMapper;

    public Integer getUserNum(){
        return userMapper.getAllUserNum();
    }

    public Integer getEquipNum(){
        return equipMapper.getEquipList().size();
    }

    public Integer getAvatarNum(){
        return avatarMapper.getAvatarList().size();
    }

    public Integer getMapNum(){
        return timeTrialMapper.getTimeTrialInfos().size();
    }
}