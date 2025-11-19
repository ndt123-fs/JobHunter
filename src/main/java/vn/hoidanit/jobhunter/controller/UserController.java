package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST
    @PostMapping("/user")
    public User createNewUser(@RequestBody User userCreate) {
        User newUser = this.userService.handleSaveUser(userCreate);
        return newUser;
    }

    // GET
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return this.userService.handleGetUserById(id);

    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        List<User> userList = this.userService.handleGetAllUser();
        return userList;

    }

    // PUT
    @PutMapping("/user")
    public User updateUser(@RequestBody User reqUser) {
        User user = this.userService.handleUpdateUser(reqUser);
        return user;

    }

    // DELETE
    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        this.userService.handleDeleteUser(id);
        return "delete user successfully";
    }

}
