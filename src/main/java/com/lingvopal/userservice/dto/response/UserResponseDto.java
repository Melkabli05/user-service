package com.lingvopal.userservice.dto.response;

import com.lingvopal.userservice.enums.Interests;
import com.neovisionaries.i18n.LanguageCode;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String country;
    private String bio;
    private String authId;

    private String identification;

    private List<Interests> interests;

    private LanguageCode nativeLanguages;
    private Map<LanguageCode, String> spokenLanguages;
    private Map<LanguageCode, String> learningLanguages;

}
