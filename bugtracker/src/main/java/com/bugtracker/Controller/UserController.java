package com.bugtracker.Controller;

import com.bugtracker.Model.User;
import com.bugtracker.Repository.UserRepository;
import com.bugtracker.ResourceNotFoundException.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/User-List")
    public List<User> list(){
        return this.userRepository.findAll();
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User users){
        return this.userRepository.save(users);
    }

    @DeleteMapping("/delete-User/{userId}")
    public ResponseEntity<Map<String, Boolean> > deleteUser(@PathVariable Long userId){
        User users = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not exist with id : " + userId));
        this.userRepository.deleteById(userId);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/User/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User users = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id : " + userId));
        return ResponseEntity.ok(users);

    }

    @PutMapping("/Update-User/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User userInput,@PathVariable Long userId){
        User users = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not exist with id : " + userId));
        users.setUserName(userInput.getUserName());
        users.setFirstName(userInput.getFirstName());
        users.setLastName(userInput.getLastName());
        User updateUser = userRepository.save(users);
        return ResponseEntity.ok(updateUser);

    }


}

