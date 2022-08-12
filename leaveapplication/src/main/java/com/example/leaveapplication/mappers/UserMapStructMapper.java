package com.example.leaveapplication.mappers;

import com.example.leaveapplication.dto.UserDTO;
import com.example.leaveapplication.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStructMapper {
    public UserDTO mapToDTO(User user);
    public User mapToEntity(UserDTO userDTO);

    User mapToEntityWithManager(UserDTO userDTO);
    //public User mapToEntityWithManager(UserDTO userDTO);
}
