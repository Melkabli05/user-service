package com.lingvopal.userservice.dto;

import com.lingvopal.userservice.enums.*;
import com.neovisionaries.i18n.LanguageCode;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class UserCreationDto {

    @NotBlank(message = "Username is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String authId;

    private String identification;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String profilePictureUrl;

    @Size(max = 500)
    private String bio;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Country is required")
    private String country;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Role is required")
    private UserRole role;

    @NotNull(message = "Status is required")
    private UserStatus status;

    @NotNull(message = "Native language is required")
    private LanguageCode nativeLanguage;

    @NotNull(message = "Spoken languages are required")
    private Map<LanguageCode, ProficiencyLevel> spokenLanguages;

    @NotNull(message = "Learning languages are required")
    private Map<LanguageCode, ProficiencyLevel> learningLanguages;

    private List<Interests> interests;
}
