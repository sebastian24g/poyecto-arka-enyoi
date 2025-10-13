package com.arka_EP.security;

import com.arka_EP.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var opt = userRepository.findByUsername(username);
        if (opt.isEmpty()) throw new UsernameNotFoundException("User not found");
        var u = opt.get();
        // For simplicity, grant ROLE_USER by default
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                .build();
    }
}
