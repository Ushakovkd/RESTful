package com.web.audio.dao;

import com.web.audio.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findUserByLogin(String login);

    List<User> getAllCustomers();

    List<User> getUsers();

    Optional<User> findUserById(int userId);

    void saveUser(User user);

    void removeUser(int id);

    void updateUserDiscountAndBlock(String userId, int discount, boolean block);
}
