package com.github.EmilyMartins.imageliteapi.domain.service;

import com.github.EmilyMartins.imageliteapi.domain.AccessToken;
import com.github.EmilyMartins.imageliteapi.domain.entity.User;

public interface UserService {
    User getByEmail(String email);
     User save(User user);
    AccessToken autheticate(String email, String password);
}
