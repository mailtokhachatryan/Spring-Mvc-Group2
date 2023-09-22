package com.smartcode.web.service.user;

import com.smartcode.web.model.UserEntity;

public interface UserService {

    void register(UserEntity user);

    void login(String username, String password);

    UserEntity getUserByEmail(String username);

    void verify(String email, String code);
}
