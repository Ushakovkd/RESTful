package com.web.audio.dto.convert;

import com.web.audio.dto.UserDto;
import com.web.audio.entity.User;
import com.web.audio.entity.field.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoConverter {

    public User convertUserDtoToUserWithCustomerRole(UserDto dto) {
        User user = new User();

        user.setLogin(dto.getLogin());
        user.setDiscount(dto.getDiscount());
        user.setBlock(dto.isBlock());
        user.setRole(Role.CUSTOMER);

        return user;
    }

    public List<UserDto> convertUserListToUserDtoList(List<User> users) {
        List<UserDto> dtos = new ArrayList<>();

        for (User user : users) {
            UserDto dto = convertUserToUserDto(user);
            dtos.add(dto);
        }
        return dtos;
    }

    public UserDto convertUserToUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setDiscount(user.getDiscount());
        dto.setBlock(user.isBlock());
        return dto;
    }
}
