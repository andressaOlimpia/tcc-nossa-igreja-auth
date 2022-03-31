package com.olimpia.tcc.nossaigrejaauthservice.service;

import com.olimpia.tcc.nossaigrejaauthservice.model.Role;
import com.olimpia.tcc.nossaigrejaauthservice.model.User;
import com.olimpia.tcc.nossaigrejaauthservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getId()+","+user.getUsername()+","+user.getFirstName()+","+user.getLastName(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                grantedAuthorities
        );
    }
}
