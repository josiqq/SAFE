package com.josiqq.safe.controller;

import com.josiqq.safe.service.VehiculoService;
import com.josiqq.safe.service.FileStorageService;
import com.josiqq.safe.model.Foto;
import com.josiqq.safe.model.Vehiculo;
import com.josiqq.safe.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FotoRepository fotoRepository;

    @InitBinder("vehiculo")
    public void initBinder(WebDataBinder binder) {
        // Excluir el campo 'fotos' del binding automático
        binder.setDisallowedFields("fotos");
    }

    @GetMapping
    public String listarVehiculos(Model model) {
        model.addAttribute("vehiculos", vehiculoService.findAll());
        return "vehiculos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculos/formulario";
    }

    @PostMapping
    public String guardarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo,
                                 @RequestParam(value = "fotos", required = false) MultipartFile[] fotos,
                                 Model model) {
        try {
            // Guardar el vehículo primero
            Vehiculo vehiculoGuardado = vehiculoService.save(vehiculo);

            // Procesar las fotos si se han subido
            if (fotos != null && fotos.length > 0) {
                for (MultipartFile foto : fotos) {
                    if (!foto.isEmpty()) {
                        try {
                            String nombreArchivo = fileStorageService.store(foto);
                            Foto nuevaFoto = new Foto(nombreArchivo, vehiculoGuardado);
                            fotoRepository.save(nuevaFoto);
                        } catch (Exception e) {
                            // Log del error, pero no interrumpir el flujo
                            e.printStackTrace();
                        }
                    }
                }
            }

            return "redirect:/vehiculos";
        } catch (IllegalStateException e) {
            // Manejar error de placa duplicada
            model.addAttribute("vehiculo", vehiculo);
            model.addAttribute("error", e.getMessage());
            return "vehiculos/formulario";
        }
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Vehiculo> vehiculoOpt = vehiculoService.findById(id);
        if (vehiculoOpt.isEmpty()) {
            return "redirect:/vehiculos";
        }
        model.addAttribute("vehiculo", vehiculoOpt.get());
        return "vehiculos/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarVehiculo(@PathVariable Long id, 
                                    @ModelAttribute("vehiculo") Vehiculo vehiculo,
                                    @RequestParam(value = "fotos", required = false) MultipartFile[] fotos,
                                    Model model) {
        try {
            vehiculo.setId(id);
            Vehiculo vehiculoActualizado = vehiculoService.save(vehiculo);

            // Procesar nuevas fotos si se han subido
            if (fotos != null && fotos.length > 0) {
                for (MultipartFile foto : fotos) {
                    if (!foto.isEmpty()) {
                        try {
                            String nombreArchivo = fileStorageService.store(foto);
                            Foto nuevaFoto = new Foto(nombreArchivo, vehiculoActualizado);
                            fotoRepository.save(nuevaFoto);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return "redirect:/vehiculos";
        } catch (IllegalStateException e) {
            // Manejar error de placa duplicada
            model.addAttribute("vehiculo", vehiculo);
            model.addAttribute("error", e.getMessage());
            return "vehiculos/formulario";
        }
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.deleteById(id);
        return "redirect:/vehiculos";
    }

    @PostMapping("/{vehiculoId}/fotos/{fotoId}/eliminar")
    @ResponseBody
    public String eliminarFoto(@PathVariable Long vehiculoId, @PathVariable Long fotoId) {
        try {
            fotoRepository.deleteById(fotoId);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
}