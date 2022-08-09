package com.example.leaveapplication.mappers;

import com.example.leaveapplication.dto.UserDTO;
import com.example.leaveapplication.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements UserMapStructMapper{


   /* public static UserDTO mapToDTOWithManager(User user){
        UserDTO userDTO = mapToDto(user);
        if(user.getManagerId()!=null){
            userDTO.setManager(UserMapper.mapToDto(user.getManagerId()));
        }
        return userDTO;
    }*/

    @Override
    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }

    @Override
    public User mapToEntity(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());

        return user;

    }

    /*@Override
    public User mapToEntityWithManager(UserDTO userDTO) {
        User user = mapToEntity(userDTO);
        if(user.getManagerId()!=null){
            user.setManagerId(mapToEntity(userDTO.getManager()));
        }
        return user;
    }*/
    /*public static User mapToEntityWithManager(UserDTO userDTO){

    }*/
}
