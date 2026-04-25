package com.futbol.app.controladores;

import com.futbol.app.entidades.Entrenador;
import com.futbol.app.repositorios.EntrenadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/entrenadores")
public class EntrenadorWebControlador {

    @Autowired
    private EntrenadorRepositorio entrenadorRepositorio;

    // GET /entrenadores → listar todos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entrenadores", entrenadorRepositorio.findAll());
        return "entrenadores";
    }

    // GET /entrenadores/nuevo → formulario nuevo entrenador
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("entrenador", new Entrenador());
        return "entrenador-form";
    }

    // GET /entrenadores/editar/{id} → formulario editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model, RedirectAttributes ra) {
        Optional<Entrenador> entrenador = entrenadorRepositorio.findById(id);
        if (entrenador.isEmpty()) {
            ra.addFlashAttribute("error", "Entrenador no encontrado.");
            return "redirect:/entrenadores";
        }
        model.addAttribute("entrenador", entrenador.get());
        return "entrenador-form";
    }

    // POST /entrenadores/guardar → guardar (crear y editar)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Entrenador entrenador, RedirectAttributes ra) {
        entrenadorRepositorio.save(entrenador);
        ra.addFlashAttribute("success", "Entrenador guardado correctamente.");
        return "redirect:/entrenadores";
    }

    // GET /entrenadores/eliminar/{id} → eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes ra) {
        if (entrenadorRepositorio.existsById(id)) {
            entrenadorRepositorio.deleteById(id);
            ra.addFlashAttribute("success", "Entrenador eliminado correctamente.");
        } else {
            ra.addFlashAttribute("error", "Entrenador no encontrado.");
        }
        return "redirect:/entrenadores";
    }
}