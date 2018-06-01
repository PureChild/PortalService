package com.example.project_practice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id){
        return userRepository.findById(id).get();
    }

    @GetMapping("/list")
    public List<User> list(){
        return userRepository.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping
    public void modify(@RequestBody User user){
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        userRepository.delete(userRepository.findById(id).get());
    }
}
