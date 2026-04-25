package com.futbol.app.controladores;

import com.futbol.app.entidades.Competicion;
import com.futbol.app.repositorios.CompeticionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/competiciones")
public class CompeticionWebControlador {

    @Autowired
    private CompeticionRepositorio competicionRepositorio;

    // GET /competiciones → listar todas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("competiciones", competicionRepositorio.findAll());
        return "competiciones";
    }

    // GET /competiciones/nuevo → formulario nueva competición
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("competicion", new Competicion());
        return "competicion-form";
    }

    // GET /competiciones/editar/{id} → formulario editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model, RedirectAttributes ra) {
        Optional<Competicion> competicion = competicionRepositorio.findById(id);
        if (competicion.isEmpty()) {
            ra.addFlashAttribute("error", "Competición no encontrada.");
            return "redirect:/competiciones";
        }
        model.addAttribute("competicion", competicion.get());
        return "competicion-form";
    }

    // POST /competiciones/guardar → guardar (crear y editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Competicion competicion, RedirectAttributes ra) {
        competicionRepositorio.save(competicion);
        ra.addFlashAttribute("success", "Competición guardada correctamente.");
        return "redirect:/competiciones";
    }

    // GET /competiciones/eliminar/{id} → eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes ra) {
        if (competicionRepositorio.existsById(id)) {
            competicionRepositorio.deleteById(id);
            ra.addFlashAttribute("success", "Competición eliminada correctamente.");
        } else {
            ra.addFlashAttribute("error", "Competición no encontrada.");
        }
        return "redirect:/competiciones";
    }
}