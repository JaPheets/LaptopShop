package com.example.laptopshop.controller.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import com.example.laptopshop.domian.User;
import com.example.laptopshop.domian.dto.RegisterDTO;
import com.example.laptopshop.service.UploadService;
import com.example.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UploadService uploadService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.uploadService = uploadService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("admin@gmail.com");
        // System.out.println(arrUsers);
        model.addAttribute("HomePage", "test");
        model.addAttribute("Page", "From controller with model");
        return "home";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        System.out.println("Check path id = " + id);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @RequestMapping("/admin/user/create")
    public String getCreatePage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model, @ModelAttribute("newUser") User person,
            @RequestParam("upFile") MultipartFile file) {
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(person.getPassword());
        person.setPassword(hashPassword);
        person.setRole(this.userService.getRoleByName(person.getRole().getName()));
        this.userService.handleSaveUser(person);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUser(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User person) {
        User currentUser = this.userService.getUserById(person.getId());
        if (currentUser != null) {
            currentUser.setAddress(person.getAddress());
            currentUser.setFullname(person.getFullname());
            currentUser.setPhone(person.getPhone());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUser(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User person) {
        this.userService.deleteUser(person.getId());
        return "redirect:/admin/user";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") RegisterDTO registerDTO) {
        User user = this.userService.registerDTOtoUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));

        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "client/auth/login";
    }

    @PostMapping("/login")
    public String getLoginPage(Model model, String abc) {
        return "redirect:/home";
    }

    @GetMapping("/access_deny")
    public String getDenyPage(Model model) {

        return "client/auth/deny";
    }
}
