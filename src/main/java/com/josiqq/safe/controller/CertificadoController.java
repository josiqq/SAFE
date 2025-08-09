package com.josiqq.safe.controller;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.Certificado;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bomberos/{bomberoId}/certificados") // URLs anidadas
public class CertificadoController {

    @Autowired
    private CertificadoService certificadoService;

    @Autowired
    private BomberoService bomberoService;

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoCertificado(@PathVariable Long bomberoId, Model model) {
        Bombero bombero = bomberoService.findById(bomberoId).orElseThrow();
        Certificado certificado = new Certificado();
        certificado.setBombero(bombero); // Asociamos el bombero
        
        model.addAttribute("certificado", certificado);
        return "certificados/formulario"; // /templates/certificados/formulario.html
    }

    @PostMapping
    public String guardarCertificado(@PathVariable Long bomberoId, @ModelAttribute("certificado") Certificado certificado) {
        Bombero bombero = bomberoService.findById(bomberoId).orElseThrow();
        certificado.setBombero(bombero);
        certificadoService.save(certificado);
        return "redirect:/bomberos"; // O al detalle del bombero
    }
    
    @GetMapping("/{certificadoId}/eliminar")
    public String eliminarCertificado(@PathVariable Long bomberoId, @PathVariable Long certificadoId) {
        certificadoService.deleteById(certificadoId);
        return "redirect:/bomberos"; // O al detalle del bombero
    }
}