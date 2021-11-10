package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ActivityRequest;
import com.alkemy.ong.model.Activity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ActivityMapper {

    @Autowired
    ModelMapper modelMapper;

    public void dtoToEntity(ActivityRequest activityReq, Activity activity) {
        modelMapper.map(activityReq, activity);       
    }
}
