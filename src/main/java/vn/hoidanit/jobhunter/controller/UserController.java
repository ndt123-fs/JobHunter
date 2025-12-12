package vn.hoidanit.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.utils.error.IdInvalidException;

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
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // POST
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User userCreate) {
        String hashPassword = this.passwordEncoder.encode(userCreate.getPassword());
        userCreate.setPassword(hashPassword);
        User newUser = this.userService.handleSaveUser(userCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    // GET
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleGetUserById(id));

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = this.userService.handleGetAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(userList);

    }

    // PUT
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User reqUser) {
        User user = this.userService.handleUpdateUser(reqUser);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    // DELETE
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) throws Exception {
        if (id > 1500) {
            throw new IdInvalidException("Id not exist in Database !");
        }
        this.userService.handleDeleteUser(id);
        // return ResponseEntity.status(HttpStatus.OK).body("delete user successfully");
        return ResponseEntity.ok("Delete user successfully");
    }

}
