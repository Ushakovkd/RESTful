package com.web.audio.service;

import com.web.audio.dto.SongDto;
import com.web.audio.dto.UserDto;
import com.web.audio.entity.Song;
import com.web.audio.exception.ServiceException;

import java.util.List;

public interface UserService {

    UserDto findUserByLogin(String login);

    List<UserDto> getAllCustomers();

    List<UserDto> getUsers();

    UserDto findUserById(int id) throws ServiceException;

    void createCustomer(String login, String password, String repeatPassword) throws ServiceException;

    void removeUser(int id);

    void updateCustomerDiscountAndBlock(String customerId, UserDto userDto);

    void addPaidSongsToUser(int userId, List<Song> songs);

    void deleteSongFromUserSongs(int userId, Song song);

    boolean hasCustomerTheSong(int userId, Song song);

    List<SongDto> findUserSongsByUserId(int userId);
}
