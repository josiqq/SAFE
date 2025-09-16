package com.josiqq.safe.controller;

import com.josiqq.safe.model.Incidente;
import com.josiqq.safe.model.Foto;
import com.josiqq.safe.service.IncidenteService;
import com.josiqq.safe.service.FileStorageService;
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
@RequestMapping("/incidentes")
public class IncidenteController {

    @Autowired
    private IncidenteService incidenteService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FotoRepository fotoRepository;

    @InitBinder("incidente")
    public void initBinder(WebDataBinder binder) {
        // Excluir el campo 'fotos' del binding automático
        binder.setDisallowedFields("fotos");
    }

    /**
     * READ (All) - Muestra una lista de todos los incidentes.
     * GET /incidentes
     */
    @GetMapping
    public String listarIncidentes(Model model) {
        List<Incidente> listaIncidentes = incidenteService.findAll();
        model.addAttribute("incidentes", listaIncidentes);
        return "incidentes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoIncidente(Model model) {
        model.addAttribute("incidente", new Incidente());
        return "incidentes/formulario";
    }

    @PostMapping
    public String guardarIncidente(@ModelAttribute("incidente") Incidente incidente,
                                  @RequestParam(value = "fotos", required = false) MultipartFile[] fotos,
                                  Model model) {
        try {
            // Guardar el incidente primero
            Incidente incidenteGuardado = incidenteService.save(incidente);

            // Procesar las fotos si se han subido
            if (fotos != null && fotos.length > 0) {
                for (MultipartFile foto : fotos) {
                    if (!foto.isEmpty()) {
                        try {
                            String nombreArchivo = fileStorageService.store(foto);
                            Foto nuevaFoto = new Foto(nombreArchivo, incidenteGuardado);
                            fotoRepository.save(nuevaFoto);
                        } catch (Exception e) {
                            // Log del error, pero no interrumpir el flujo
                            e.printStackTrace();
                        }
                    }
                }
            }

            return "redirect:/incidentes";
        } catch (Exception e) {
            model.addAttribute("incidente", incidente);
            model.addAttribute("error", "Error al guardar el incidente: " + e.getMessage());
            return "incidentes/formulario";
        }
    }

    @GetMapping("/{id}")
    public String verDetalleIncidente(@PathVariable Long id, Model model) {
        Optional<Incidente> incidenteOpt = incidenteService.findById(id);
        if (incidenteOpt.isEmpty()) {
            return "redirect:/incidentes"; // O una página de error 404
        }
        model.addAttribute("incidente", incidenteOpt.get());
        return "incidentes/detalle"; // Necesitarás crear /templates/incidentes/detalle.html
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Incidente> incidenteOpt = incidenteService.findById(id);
        if (incidenteOpt.isEmpty()) {
            return "redirect:/incidentes";
        }
        model.addAttribute("incidente", incidenteOpt.get());
        return "incidentes/formulario"; // Reutilizamos la misma vista del formulario
    }

    @PostMapping("/{id}")
    public String actualizarIncidente(@PathVariable Long id, 
                                    @ModelAttribute("incidente") Incidente incidente,
                                    @RequestParam(value = "fotos", required = false) MultipartFile[] fotos,
                                    Model model) {
        try {
            incidente.setId(id); // Aseguramos que estamos actualizando el incidente correcto
            Incidente incidenteActualizado = incidenteService.save(incidente);

            // Procesar nuevas fotos si se han subido
            if (fotos != null && fotos.length > 0) {
                for (MultipartFile foto : fotos) {
                    if (!foto.isEmpty()) {
                        try {
                            String nombreArchivo = fileStorageService.store(foto);
                            Foto nuevaFoto = new Foto(nombreArchivo, incidenteActualizado);
                            fotoRepository.save(nuevaFoto);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return "redirect:/incidentes";
        } catch (Exception e) {
            model.addAttribute("incidente", incidente);
            model.addAttribute("error", "Error al actualizar el incidente: " + e.getMessage());
            return "incidentes/formulario";
        }
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarIncidente(@PathVariable Long id) {
        incidenteService.deleteById(id);
        return "redirect:/incidentes";
    }

    /**
     * Eliminar una foto específica de un incidente
     * POST /incidentes/{incidenteId}/fotos/{fotoId}/eliminar
     */
    @PostMapping("/{incidenteId}/fotos/{fotoId}/eliminar")
    @ResponseBody
    public String eliminarFoto(@PathVariable Long incidenteId, @PathVariable Long fotoId) {
        try {
            fotoRepository.deleteById(fotoId);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
}
