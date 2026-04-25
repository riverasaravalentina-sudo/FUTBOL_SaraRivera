package com.futbol.app.controladores;

import com.futbol.app.entidades.Club;
import com.futbol.app.entidades.Jugador;
import com.futbol.app.repositorios.ClubRepositorio;
import com.futbol.app.repositorios.JugadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jugadores")
public class JugadorWebControlador {

    @Autowired
    private JugadorRepositorio jugadorRepositorio;

    @Autowired
    private ClubRepositorio clubRepositorio;

    // GET /jugadores → listar todos los jugadores (vista general)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("jugadores", jugadorRepositorio.findAll());
        model.addAttribute("clubes", clubRepositorio.findAll());
        return "jugadores";
    }

    // GET /jugadores/{id}/editar → formulario editar jugador
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable String id, Model model, RedirectAttributes ra) {
        Optional<Jugador> jugador = jugadorRepositorio.findById(id);
        if (jugador.isEmpty()) {
            ra.addFlashAttribute("error", "Jugador no encontrado.");
            return "redirect:/listar";
        }
        model.addAttribute("jugador", jugador.get());
        return "jugadores";
    }

    // GET /jugadores/{id}/eliminar → eliminar jugador
    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable String id, RedirectAttributes ra) {
        Optional<Jugador> jugador = jugadorRepositorio.findById(id);
        if (jugador.isPresent()) {
            String clubId = jugador.get().getClubId();
            jugadorRepositorio.deleteById(id);
            ra.addFlashAttribute("success", "Jugador eliminado correctamente.");
            return "redirect:/club/" + clubId + "/jugadores";
        }
        ra.addFlashAttribute("error", "Jugador no encontrado.");
        return "redirect:/listar";
    }
}