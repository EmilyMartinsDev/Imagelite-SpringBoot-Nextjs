package com.github.EmilyMartins.imageliteapi.application.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.github.EmilyMartins.imageliteapi.domain.AccessToken;
import com.github.EmilyMartins.imageliteapi.domain.entity.User;
import com.github.EmilyMartins.imageliteapi.domain.exception.DuplicatedTupleException;
import com.github.EmilyMartins.imageliteapi.domain.service.UserService;
import com.github.EmilyMartins.imageliteapi.infra.repository.UserRepository;
import com.github.EmilyMartins.imageliteapi.application.jwt.JwtService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        var possibleUser = getByEmail(user.getEmail());
        if(possibleUser != null){
            throw new DuplicatedTupleException("User already exists!");
        }
        encodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public AccessToken autheticate(String email, String password) {
     
        var user = getByEmail(email);
        if(user == null){
            return null;
        }

        boolean matches = passwordEncoder.matches(password, user.getPassword());

        if(matches){
            return jwtService.generateToken(user);
        }

        return null;
    }

    private void encodePassword(User user){
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
    }
}
