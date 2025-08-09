package com.josiqq.safe.controller;

import com.josiqq.safe.models.BitacoraPostMision;
import com.josiqq.safe.service.BitacoraPostMisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/bitacoras")
public class BitacoraPostMisionController {

    @Autowired
    private BitacoraPostMisionService bitacoraService;

    @GetMapping("/{id}")
    public String verBitacora(@PathVariable Long id, Model model) {
        Optional<BitacoraPostMision> bitacoraOpt = bitacoraService.findById(id);
        if (bitacoraOpt.isEmpty()) {
            return "redirect:/incidentes"; // Si no existe, volver a incidentes
        }
        model.addAttribute("bitacora", bitacoraOpt.get());
        return "bitacoras/detalle"; // /templates/bitacoras/detalle.html
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<BitacoraPostMision> bitacoraOpt = bitacoraService.findById(id);
        if (bitacoraOpt.isEmpty()) {
            return "redirect:/incidentes";
        }
        model.addAttribute("bitacora", bitacoraOpt.get());
        return "bitacoras/formulario"; // /templates/bitacoras/formulario.html
    }

    @PostMapping("/{id}")
    public String actualizarBitacora(@PathVariable Long id, @ModelAttribute("bitacora") BitacoraPostMision bitacora) {
        // Obtenemos el incidente asociado para no perderlo
        BitacoraPostMision bitacoraExistente = bitacoraService.findById(id).orElseThrow();
        bitacora.setId(id);
        bitacora.setIncidente(bitacoraExistente.getIncidente()); // Re-asociamos el incidente
        
        bitacoraService.save(bitacora);
        return "redirect:/incidentes/" + bitacora.getIncidente().getId(); // Volver al detalle del incidente
    }
}