package vn.hoidanit.jobhunter.service.impl;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.User;
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
    public List<User> handleGetAllUser() {
        List<User> lstUser = this.userRepository.findAll();
        return lstUser;

    }

    @Override
    public User handleUpdateUser(User reqUser) {
        User currentUser = this.handleGetUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setName(reqUser.getName());
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setPassword(reqUser.getPassword());
            // update
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;

    }

}
