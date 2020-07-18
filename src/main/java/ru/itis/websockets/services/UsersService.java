package ru.itis.websockets.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.websockets.dto.UserDto;
import ru.itis.websockets.models.User;

import java.util.List;

public interface UsersService {
    List<UserDto> getUsers();

    UserDto getConcreteUser(Long userId);
}
