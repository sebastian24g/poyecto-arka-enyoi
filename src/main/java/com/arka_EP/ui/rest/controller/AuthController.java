package com.arka_EP.ui.rest.controller;

import com.arka_EP.application.dto.AuthRequest;
import com.arka_EP.application.dto.AuthResponse;
import com.arka_EP.domain.model.User;
import com.arka_EP.domain.repository.UserRepository;
import com.arka_EP.application.dto.RegisterUserDto;
import com.arka_EP.application.mapper.UserMapper;
import com.arka_EP.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto dto) {
        var user = UserMapper.toDomain(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var saved = userRepository.save(user);
        return ResponseEntity.ok(UserMapper.toDto(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {
        var opt = userRepository.findByUsername(req.getUsername());
        if (opt.isEmpty()) return ResponseEntity.status(401).build();
        var u = opt.get();
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) return ResponseEntity.status(401).build();
        String token = jwtUtil.generateToken(u.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
