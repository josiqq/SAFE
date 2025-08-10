package com.josiqq.safe.security;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.repository.BomberoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BomberoRepository bomberoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos al bombero en nuestra base de datos por su username
        Bombero bombero = bomberoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con el username: " + username));

        // 2. Creamos la lista de "roles" o "permisos" del usuario
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (bombero.getRol() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + bombero.getRol().getNombre().toUpperCase()));
        } else {
            // Asignar un rol por defecto si no tiene uno
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // 3. Devolvemos el objeto UserDetails que Spring Security entiende
        return new User(
            bombero.getUsername(),
            bombero.getPassword(),
            authorities
        );
    }
}