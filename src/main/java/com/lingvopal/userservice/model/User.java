package com.lingvopal.userservice.model;

import com.lingvopal.userservice.enums.*;
import com.neovisionaries.i18n.LanguageCode;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank(message = "Username is required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @UniqueElements(message = "Email already exists")
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

    @NotBlank(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Role is required")
    private UserRole role;

    @NotNull(message = "Status is required")
    private UserStatus status;

    @NotNull(message = "Native language is required")
    private LanguageCode nativeLanguage;

    private Map<LanguageCode, ProficiencyLevel> spokenLanguages;

    private Map<LanguageCode, ProficiencyLevel> learningLanguages;

    private List<Interests> interests;

    @DBRef(lazy = true)
    private List<User> followers;

    @DBRef(lazy = true)
    private List<User> following;

    @DBRef(lazy = true)
    private List<User> blockedUsers;

    @DBRef(lazy = true)
    private List<User> blockedBy;

    @DBRef(lazy = true)
    private List<String> reviews;

    @DBRef(lazy = true)
    private List<Integer> stars;

    private String linkedSocialMediaAccount;
}
