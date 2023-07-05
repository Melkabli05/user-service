package com.lingvopal.userservice.dto.mapper;

import com.lingvopal.userservice.dto.UserCreationDto;
import com.lingvopal.userservice.dto.UserUpdateDto;
import com.lingvopal.userservice.dto.response.UserResponseDto;
import com.lingvopal.userservice.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public User mapToUser(UserCreationDto userCreationDto) {
        return modelMapper.map(userCreationDto, User.class);
    }

    public UserResponseDto mapToUserResponseDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    public void mapToExistingUser(UserUpdateDto userUpdateDto, User existingUser) {
        modelMapper.map(userUpdateDto, existingUser);
    }
}
