package vn.hoidanit.jobhunter.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResUserDTO;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User handleSaveUser(User user) {

        return this.userRepository.save(user);

    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public void handleDeleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User handleGetUserById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public ResultPaginationDTO handleGetAllUser(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        ResultPaginationDTO result = new ResultPaginationDTO();
        // Trang nao:FE
        meta.setPage(pageable.getPageNumber() + 1);
        // So record lay theo yeu cau:FE
        meta.setPageSize(pageable.getPageSize());
        // Tong so trang hien co , chia theo pagesize cua FE
        meta.setPages(pageUser.getTotalPages());
        // Tong record hien trong trang do
        meta.setTotal(pageUser.getTotalElements());
        result.setMeta(meta);
        result.setData(pageUser.getContent()
                .stream().map(item -> new ResUserDTO(
                        item.getId(),
                        item.getName(),
                        item.getEmail(),
                        item.getAge(),
                        item.getGender(),
                        item.getAddress(),
                        item.getUpdateAt(),
                        item.getCreateAt()))
                .collect(Collectors.toList()));
        return result;

    }

    @Override
    public User handleUpdateUser(User reqUser) {
        User currentUser = this.handleGetUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setName(reqUser.getName());
            currentUser.setAge(reqUser.getAge());
            currentUser.setGender(reqUser.getGender());
            currentUser.setAddress(reqUser.getAddress());

            // update
            currentUser = this.userRepository.save(currentUser);
        }

        return currentUser;

    }

    @Override
    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    @Override
    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO resUser = new ResCreateUserDTO();
        resUser.setId(user.getId());
        resUser.setEmail(user.getEmail());
        resUser.setName(user.getName());
        resUser.setAge(user.getAge());
        resUser.setAddress(user.getAddress());
        resUser.setGender(user.getGender());
        resUser.setCreateAt(user.getCreateAt());

        return resUser;
    }

    @Override
    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO resUser = new ResUpdateUserDTO();
        resUser.setId(user.getId());
        resUser.setName(user.getName());
        resUser.setAge(user.getAge());
        resUser.setAddress(user.getAddress());
        resUser.setGender(user.getGender());
        resUser.setUpdateAt(user.getUpdateAt());

        return resUser;
    }

    @Override
    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO resUser = new ResUserDTO();
        resUser.setId(user.getId());
        resUser.setName(user.getName());
        resUser.setEmail(user.getEmail());
        resUser.setAge(user.getAge());
        resUser.setAddress(user.getAddress());
        resUser.setGender(user.getGender());
        resUser.setUpdateAt(user.getUpdateAt());
        resUser.setCreateAt(user.getCreateAt());

        return resUser;
    }

}
