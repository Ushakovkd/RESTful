package com.web.audio.service.impl;

import com.web.audio.dao.UserDao;
import com.web.audio.dto.SongDto;
import com.web.audio.dto.UserDto;
import com.web.audio.dto.convert.SongDtoConverter;
import com.web.audio.dto.convert.UserDtoConverter;
import com.web.audio.entity.Song;
import com.web.audio.entity.User;
import com.web.audio.entity.field.Role;
import com.web.audio.exception.ServiceException;
import com.web.audio.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private UserDtoConverter userDtoConverter;
    private SongDtoConverter songDtoConverter;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserDtoConverter userDtoConverter, SongDtoConverter songDtoConverter) {
        this.userDao = userDao;
        this.userDtoConverter = userDtoConverter;
        this.songDtoConverter = songDtoConverter;
    }

    @Override
    public UserDto findUserByLogin(String login) {
        Optional<User> optionalUser = userDao.findUserByLogin(login);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        UserDtoConverter dtoConverter = new UserDtoConverter();
        return dtoConverter.convertUserToUserDto(user);
    }

    @Override
    public List<UserDto> getAllCustomers() {
        List<User> users = userDao.getAllCustomers();
        return userDtoConverter.convertUserListToUserDtoList(users);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userDao.getUsers();
        return userDtoConverter.convertUserListToUserDtoList(users);
    }

    @Override
    public UserDto findUserById(int id) throws ServiceException {
        Optional<User> optionalUser = userDao.findUserById(id);
        if (optionalUser.isEmpty()) {
            throw new ServiceException("User not found");
        }
        User user = optionalUser.get();
        return userDtoConverter.convertUserToUserDto(user);
    }

    @Override
    public void createCustomer(String login, String password, String repeatPassword) throws ServiceException {
        if (!(password.equals(repeatPassword))) {
            throw new ServiceException("passwords are not equal");
        }

        Optional<User> foundUser = userDao.findUserByLogin(login);
        if (foundUser.isPresent()) {
            throw new ServiceException("That user already exists");
        }
        User user = new User();
        user.setLogin(login);
        String hashPassword = DigestUtils.md5Hex(password);
        user.setPassword(hashPassword);
        user.setRole(Role.CUSTOMER);
        userDao.saveUser(user);
    }

    @Override
    public void removeUser(int id) {
        userDao.removeUser(id);
    }

    @Override
    public void updateCustomerDiscountAndBlock(String customerId, UserDto userDto) {
        int discount = userDto.getDiscount();
        boolean block = userDto.isBlock();
        userDao.updateUserDiscountAndBlock(customerId, discount, block);
    }

    @Override
    public void addPaidSongsToUser(int userId, List<Song> songs) {
        User user = getUserById(userId);
        List<Song> userSongs = user.getSongs();
        userSongs.addAll(songs);
        userDao.saveUser(user);
    }

    @Override
    public void deleteSongFromUserSongs(int userId, Song song) {
        User user = getUserById(userId);
        List<Song> userSongs = user.getSongs();
        userSongs.remove(song);
        userDao.saveUser(user);
    }

    @Override
    public boolean hasCustomerTheSong(int userId, Song song) {
        User user = getUserById(userId);
        List<Song> userSongs = user.getSongs();
        return userSongs.contains(song);
    }

    @Override
    public List<SongDto> findUserSongsByUserId(int userId) {
        User user = getUserById(userId);
        List<Song> userSongs = user.getSongs();
        return songDtoConverter.convertSongListToSongDtoList(userSongs);
    }

    private User getUserById(int id) {
        Optional<User> optionalUser = userDao.findUserById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return optionalUser.get();
    }
}
