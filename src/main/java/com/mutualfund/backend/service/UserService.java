package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.RegisterRequest;
import com.mutualfund.backend.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO register(RegisterRequest request);
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void toggleUserStatus(Long id);
}