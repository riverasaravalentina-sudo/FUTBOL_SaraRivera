package com.futbol.app.controladores;

import com.futbol.app.entidades.Asociacion;
import com.futbol.app.repositorios.AsociacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/asociaciones")
public class AsociacionWebControlador {

    @Autowired
    private AsociacionRepositorio asociacionRepositorio;

    // GET /asociaciones → listar todas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("asociaciones", asociacionRepositorio.findAll());
        return "asociaciones";
    }

    // GET /asociaciones/nuevo → formulario nueva asociación
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("asociacion", new Asociacion());
        return "asociacion-form";
    }

    // GET /asociaciones/editar/{id} → formulario editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model, RedirectAttributes ra) {
        Optional<Asociacion> asociacion = asociacionRepositorio.findById(id);
        if (asociacion.isEmpty()) {
            ra.addFlashAttribute("error", "Asociación no encontrada.");
            return "redirect:/asociaciones";
        }
        model.addAttribute("asociacion", asociacion.get());
        return "asociacion-form";
    }

    // POST /asociaciones/guardar → guardar (crear y editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Asociacion asociacion, RedirectAttributes ra) {
        asociacionRepositorio.save(asociacion);
        ra.addFlashAttribute("success", "Asociación guardada correctamente.");
        return "redirect:/asociaciones";
    }

    // GET /asociaciones/eliminar/{id} → eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes ra) {
        if (asociacionRepositorio.existsById(id)) {
            asociacionRepositorio.deleteById(id);
            ra.addFlashAttribute("success", "Asociación eliminada correctamente.");
        } else {
            ra.addFlashAttribute("error", "Asociación no encontrada.");
        }
        return "redirect:/asociaciones";
    }
}