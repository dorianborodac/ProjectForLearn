package org.example.ServiceImp;


import lombok.RequiredArgsConstructor;
import org.example.Configuration.ModelMapperConfiguration;
import org.example.Dto.UserDto;
import org.example.Entity.User;
import org.example.Repository.UserRepository;
import org.example.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final ModelMapperConfiguration modelMapperConfiguration;
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(modelMapperConfiguration.modelMapperConfig().map(
                userDto, User.class
        ));
        return modelMapperConfiguration.modelMapperConfig().map(savedUser,UserDto.class);
    }

    @Override
    public void deleteUser(UserDto userDto) {

    }

    @Override
    public UserDto getUser(Long id) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        return null;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapperConfiguration.modelMapperConfig().map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<UserDto> findUserById(Long id) {
        Optional<UserDto> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User with id " + id + "Not found");
        }
        return ResponseEntity.ok(user.get());
    }
}
