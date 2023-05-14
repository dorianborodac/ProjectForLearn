package org.example.Service;

import org.example.Dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    void deleteUser(UserDto userDto);

    UserDto getUser(Long id);

    UserDto updateUser(UserDto userDto);

    ResponseEntity<UserDto> findUserById(Long id);

    List<UserDto> findAllUsers();

}
