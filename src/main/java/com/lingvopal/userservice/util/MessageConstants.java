package com.lingvopal.userservice.util;

public class MessageConstants {

  public static final String USER_CREATED = "User created successfully";
  public static final String USER_UPDATED = "User updated successfully";
  public static final String USER_DELETED = "User deleted successfully";
  public static final String USER_RETRIEVED = "User retrieved successfully";
  public static final String USERS_RETRIEVED = "Users retrieved successfully";
  public static final String BAD_REQUEST = "Bad request";
  public static final String USER_NOT_FOUND = "User not found";
  public static final String USERS_NOT_FOUND = "Users not found";
  public static final String USER_ALREADY_EXISTS = "User already exists";
  public static final String INTERNAL_SERVER_ERROR = "Internal server error";

  private MessageConstants() {
    throw new IllegalStateException("Utility class");
  }
}