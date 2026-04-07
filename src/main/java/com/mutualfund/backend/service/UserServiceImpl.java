package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.RegisterRequest;
import com.mutualfund.backend.dto.UserDTO;
import com.mutualfund.backend.entity.User;
import com.mutualfund.backend.exception.ResourceNotFoundException;
import com.mutualfund.backend.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO register(RegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(mapRole(request.getRole()));
        user.setStatus(User.Status.ACTIVE);

        User saved = userRepo.save(user);
        return mapToDTO(saved);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setFullName(userDTO.getFullName());
        user.setPhone(userDTO.getPhone());
        user.setPan(userDTO.getPan());
        user.setAddress(userDTO.getAddress());
        user.setRiskAppetite(userDTO.getRiskAppetite());
        user.setInvestmentHorizon(userDTO.getInvestmentHorizon());
        user.setSipBudget(userDTO.getSipBudget());

        User updated = userRepo.save(user);
        return mapToDTO(updated);
    }

    @Override
    public void toggleUserStatus(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (user.getStatus() == User.Status.ACTIVE) {
            user.setStatus(User.Status.INACTIVE);
        } else {
            user.setStatus(User.Status.ACTIVE);
        }
        userRepo.save(user);
    }

    private User.Role mapRole(String role) {
        switch (role) {
            case "Admin": return User.Role.ADMIN;
            case "Financial Advisor": return User.Role.FINANCIAL_ADVISOR;
            case "Data Analyst": return User.Role.DATA_ANALYST;
            default: return User.Role.INVESTOR;
        }
    }

    public UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setStatus(user.getStatus().name());
        dto.setPhone(user.getPhone());
        dto.setPan(user.getPan());
        dto.setAddress(user.getAddress());
        dto.setProfilePic(user.getProfilePic());
        dto.setRiskAppetite(user.getRiskAppetite());
        dto.setInvestmentHorizon(user.getInvestmentHorizon());
        dto.setSipBudget(user.getSipBudget());
        dto.setJoinDate(user.getJoinDate());
        return dto;
    }
}