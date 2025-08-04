package com.josiqq.safe.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Bombero implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // El email será el nombre de usuario
    private String email;

    @Column(nullable = false)
    private String password; // Necesitamos un campo para la contraseña

    private String nombre;
    private String apellido;
    private String dni;
    private Date fechaNacimiento;
    private String direccion;
    private String telefono;

    @Lob
    private String historialMedico;

    @OneToMany(mappedBy = "bombero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificado> certificados;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @ManyToMany(mappedBy = "bomberos")
    private Set<Turno> turnos;

    // --- Métodos de UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aquí le damos a Spring el rol del bombero (ej. "ROLE_JEFE", "ROLE_BOMBERO")
        // Spring Security requiere que los roles empiecen con el prefijo "ROLE_"
        if (rol != null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.getNombre().toUpperCase()));
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        // Usaremos el email como "username" para el login
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // La cuenta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // La cuenta nunca se bloquea
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Las credenciales nunca expiran
    }

    @Override
    public boolean isEnabled() {
        return true; // La cuenta siempre está habilitada
    }

    // --- Getters y Setters para los nuevos campos ---

    public void setPassword(String password) {
        this.password = password;
    }
    
    // ... otros getters y setters que necesites ...
}