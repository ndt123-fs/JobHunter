package vn.hoidanit.jobhunter.service;

import java.util.List;

import vn.hoidanit.jobhunter.domain.User;

public interface UserService {

    User handleSaveUser(User user);

    User handleGetUserById(Long id);

    List<User> handleGetAllUser();

    User handleUpdateUser(User reqUser);

    void handleDeleteUser(Long id);

    User handleGetUserByUsername(String email);

}
