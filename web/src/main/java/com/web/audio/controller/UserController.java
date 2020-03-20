package com.web.audio.controller;

import com.web.audio.dto.UserDto;
import com.web.audio.exception.ServiceException;
import com.web.audio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") int id) throws ServiceException {
        return userService.findUserById(id);
    }

    @GetMapping("/customers")
    public List<UserDto> getCustomers() {
        return userService.getAllCustomers();
    }

    @PostMapping("/customers")
    public void createCustomer(@Valid @RequestBody Map<String, String> params) throws ServiceException {
        String login = params.get("login");
        String password = params.get("password");
        String passwordRepeat = params.get("repeatPassword");
        userService.createCustomer(login, password, passwordRepeat);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
    }

    @PatchMapping("/{id}")
    public void updateCustomerBlockDiscount(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        userService.updateCustomerDiscountAndBlock(id, userDto);
    }
}
