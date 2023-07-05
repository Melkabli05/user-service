package com.lingvopal.userservice.dto;

import com.lingvopal.userservice.enums.Interests;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDto {

    private String firstName;

    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    private String profilePictureUrl;

    @Size(max = 500)
    private String bio;

    private String status;

    private List<Interests> interests;
}
