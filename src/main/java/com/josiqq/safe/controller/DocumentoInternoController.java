package com.josiqq.safe.controller;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.DocumentoInterno;
import com.josiqq.safe.service.DocumentoInternoService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.josiqq.safe.service.BomberoService;

@Controller
@RequestMapping("/documentos")
public class DocumentoInternoController {

    @Autowired
    private DocumentoInternoService documentoService;

    @Autowired
    private BomberoService bomberoService;

    @GetMapping
    public String listarDocumentos(Model model) {
        model.addAttribute("documentos", documentoService.findAll());
        return "documentos/lista"; // /templates/documentos/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoDocumento(Model model) {
        model.addAttribute("documento", new DocumentoInterno());
        model.addAttribute("bomberos", bomberoService.findAll());
        return "documentos/formulario"; // /templates/documentos/formulario.html
    }

    @PostMapping
    public String guardarDocumento(@ModelAttribute("documento") DocumentoInterno documento, Principal principal) {
        String username = principal.getName();

        Bombero autor = bomberoService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));

        documento.setAutor(autor);
        documentoService.save(documento);
        return "redirect:/documentos";
    }
}