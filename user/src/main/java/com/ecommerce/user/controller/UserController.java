package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());

    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
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
    public ResponseEntity<String> updateUser( @PathVariable Long id ,@RequestBody UserRequest updateUserRequest ){
       boolean updated = userService.updateUser(id, updateUserRequest);
       if(updated){
          return ResponseEntity.ok("User Updated Successfully");
       }
           return ResponseEntity.notFound().build();
    }
}
