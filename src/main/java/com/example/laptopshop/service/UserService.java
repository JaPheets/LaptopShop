package com.example.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.laptopshop.domian.Role;
import com.example.laptopshop.domian.User;
import com.example.laptopshop.domian.dto.RegisterDTO;
import com.example.laptopshop.repository.RoleRepository;
import com.example.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String handleHello() {
        return "Hello from Service";
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User handleSaveUser(User user) {
        User person = this.userRepository.save(user);
        System.out.println(user);
        return person;

    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();

        user.setFullname(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public User getUserByEmail(String email) {

        return this.userRepository.findByEmail(email);
    }
}
