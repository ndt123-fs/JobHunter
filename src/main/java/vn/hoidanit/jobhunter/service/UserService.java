package vn.hoidanit.jobhunter.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;

public interface UserService {

    User handleSaveUser(User user);

    User handleGetUserById(Long id);

    ResultPaginationDTO handleGetAllUser(Specification<User> spec, Pageable pageable);

    User handleUpdateUser(User reqUser);

    void handleDeleteUser(Long id);

    User handleGetUserByUsername(String email);

    boolean isEmailExist(String email);

    ResCreateUserDTO convertToResCreateUserDTO(User user);

    ResUpdateUserDTO convertToResUpdateUserDTO(User user);

    ResUserDTO convertToResUserDTO(User user);

}
