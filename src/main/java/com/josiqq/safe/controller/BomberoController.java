package com.josiqq.safe.controller;

import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.RolService;
import com.josiqq.safe.service.FileStorageService;
import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.Foto;
import com.josiqq.safe.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bomberos")
public class BomberoController {

    @Autowired
    private BomberoService bomberoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FotoRepository fotoRepository;

    @InitBinder("bombero")
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("fotos");
    }

    @GetMapping
    public String listarBomberos(Model model) {
        List<Bombero> bomberos = bomberoService.findAll();
        model.addAttribute("bomberos", bomberos);
        return "bomberos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoBombero(Model model) {
        model.addAttribute("bombero", new Bombero());
        model.addAttribute("roles", rolService.findAll());
        return "bomberos/formulario";
    }

    @PostMapping
    public String guardarBombero(@ModelAttribute("bombero") Bombero bombero,
                                @RequestParam(value = "fotos", required = false) MultipartFile[] fotos) {
        // Guardar el bombero primero
        Bombero bomberoGuardado = bomberoService.save(bombero);

        // Procesar las fotos si se han subido
        if (fotos != null && fotos.length > 0) {
            for (MultipartFile foto : fotos) {
                if (!foto.isEmpty()) {
                    try {
                        String nombreArchivo = fileStorageService.store(foto);
                        Foto nuevaFoto = new Foto(nombreArchivo, bomberoGuardado);
                        fotoRepository.save(nuevaFoto);
                    } catch (Exception e) {
                        // Log del error, pero no interrumpir el flujo
                        e.printStackTrace();
                    }
                }
            }
        }

        return "redirect:/bomberos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Bombero> bomberoOpt = bomberoService.findById(id);
        if (bomberoOpt.isEmpty()) {
            return "redirect:/bomberos";
        }
        model.addAttribute("bombero", bomberoOpt.get());
        model.addAttribute("roles", rolService.findAll());
        return "bomberos/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarBombero(@PathVariable Long id, 
                                   @ModelAttribute("bombero") Bombero bombero,
                                   @RequestParam(value = "fotos", required = false) MultipartFile[] fotos) {
        bombero.setId(id);
        Bombero bomberoActualizado = bomberoService.save(bombero);

        // Procesar nuevas fotos si se han subido
        if (fotos != null && fotos.length > 0) {
            for (MultipartFile foto : fotos) {
                if (!foto.isEmpty()) {
                    try {
                        String nombreArchivo = fileStorageService.store(foto);
                        Foto nuevaFoto = new Foto(nombreArchivo, bomberoActualizado);
                        fotoRepository.save(nuevaFoto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return "redirect:/bomberos";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarBombero(@PathVariable Long id) {
        bomberoService.deleteById(id);
        return "redirect:/bomberos";
    }

    @PostMapping("/{bomberoId}/fotos/{fotoId}/eliminar")
    @ResponseBody
    public String eliminarFoto(@PathVariable Long bomberoId, @PathVariable Long fotoId) {
        try {
            fotoRepository.deleteById(fotoId);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
}