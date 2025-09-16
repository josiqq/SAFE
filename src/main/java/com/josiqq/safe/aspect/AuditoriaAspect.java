package com.josiqq.safe.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.josiqq.safe.service.AuditoriaService;

@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private AuditoriaService auditoriaService;

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object auditarOperacion(ProceedingJoinPoint joinPoint) throws Throwable {
        // Obtener información del usuario actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Obtener información del método
        String nombreMetodo = joinPoint.getSignature().getName();
        String accion = determinarAccion(nombreMetodo);
        
        try {
            Object resultado = joinPoint.proceed();
            
            // Registrar la operación exitosa
            auditoriaService.registrarEvento(
                username, 
                accion, 
                "",//obtenerEntidad(joinPoint), 
                1L//obtenerIdEntidad(resultado)
            );
            
            return resultado;
        } catch (Exception e) {
            // Registrar operación fallida
            auditoriaService.registrarEvento(
                username, 
                accion + "_FALLIDO", 
                "",//obtenerEntidad(joinPoint), 
                1L//obtenerIdEntidad(resultado)
            );
            throw e;
        }
    }
    
    private String determinarAccion(String nombreMetodo) {
        if (nombreMetodo.contains("guardar") || nombreMetodo.contains("save")) {
            return "CREAR";
        } else if (nombreMetodo.contains("actualizar") || nombreMetodo.contains("update")) {
            return "MODIFICAR";
        } else if (nombreMetodo.contains("eliminar") || nombreMetodo.contains("delete")) {
            return "ELIMINAR";
        }
        return "OPERACION";
    }
}