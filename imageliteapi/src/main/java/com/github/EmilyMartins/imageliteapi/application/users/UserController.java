package com.github.EmilyMartins.imageliteapi.application.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.EmilyMartins.imageliteapi.domain.entity.User;
import com.github.EmilyMartins.imageliteapi.domain.exception.DuplicatedTupleException;
import com.github.EmilyMartins.imageliteapi.domain.service.UserService;
import com.github.EmilyMartins.imageliteapi.application.users.UserMapper;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping()
    public ResponseEntity save(@RequestBody UserDTO dto) {
       try {
            User user = userMapper.mapToUser(dto);
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DuplicatedTupleException e){
            Map<String, String> jsonResultado = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResultado);
        }

    }
    @PostMapping("/auth")
    public ResponseEntity autheticate(@RequestBody CredentialsDTO credentials){
        var token = userService.autheticate(credentials.getEmail(), credentials.getPassword());

        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(token);
    }

}
