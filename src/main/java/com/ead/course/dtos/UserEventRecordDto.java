package com.ead.course.dtos;

import com.ead.course.models.UserModel;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

public record UserEventRecordDto(UUID userId,
                                 String userName,
                                 String email,
                                 String fullName,
                                 String userStatus,
                                 String userType,
                                 String phoneNumber,
                                 String imageUrl,
                                 String actionType) {

    //METODO PARA FAZER CONVERSOES DE UserRecordDto para UseModel
    public UserModel convertToUserModel() {
        var userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel); //esse this é pq esta na classe dto e é ele mesmo...
        return userModel;
    }
}
