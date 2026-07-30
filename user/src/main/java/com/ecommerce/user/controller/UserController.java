package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());

    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        log.info("request received" , id);
        log.warn("request received for warning" , id);
        log.error("request denied" , id);
        log.trace("request tracing" , id);
        return userService.fetchUser(id)
                .map(ResponseEntity::ok) //.map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
       UserResponse response = userService.addUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser( @PathVariable String id ,@RequestBody UserRequest updateUserRequest ){
       boolean updated = userService.updateUser(id, updateUserRequest);
       if(updated){
          return ResponseEntity.ok("User Updated Successfully");
       }
           return ResponseEntity.notFound().build();
    }
}
