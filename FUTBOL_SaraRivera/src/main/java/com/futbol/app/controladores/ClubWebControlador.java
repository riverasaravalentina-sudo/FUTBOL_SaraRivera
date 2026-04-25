package com.futbol.app.controladores;

import com.futbol.app.entidades.Club;
import com.futbol.app.entidades.Jugador;
import com.futbol.app.repositorios.AsociacionRepositorio;
import com.futbol.app.repositorios.ClubRepositorio;
import com.futbol.app.repositorios.CompeticionRepositorio;
import com.futbol.app.repositorios.EntrenadorRepositorio;
import com.futbol.app.repositorios.JugadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ClubWebControlador {

    @Autowired
    private ClubRepositorio clubRepositorio;

    @Autowired
    private EntrenadorRepositorio entrenadorRepositorio;

    @Autowired
    private AsociacionRepositorio asociacionRepositorio;

    @Autowired
    private CompeticionRepositorio competicionRepositorio;

    @Autowired
    private JugadorRepositorio jugadorRepositorio;

    // ─── LISTAR CLUBES ───────────────────────────────────────────────────────
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("clubes", clubRepositorio.findAll());
        return "listar";
    }

    // ─── FORMULARIO NUEVO CLUB ───────────────────────────────────────────────
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("club", new Club());
        model.addAttribute("entrenadores", entrenadorRepositorio.findAll());
        model.addAttribute("asociaciones", asociacionRepositorio.findAll());
        model.addAttribute("competiciones", competicionRepositorio.findAll());
        return "form";
    }

    // ─── FORMULARIO EDITAR CLUB ──────────────────────────────────────────────
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model, RedirectAttributes ra) {
        Optional<Club> club = clubRepositorio.findById(id);
        if (club.isEmpty()) {
            ra.addFlashAttribute("error", "Club no encontrado.");
            return "redirect:/listar";
        }
        model.addAttribute("club", club.get());
        model.addAttribute("entrenadores", entrenadorRepositorio.findAll());
        model.addAttribute("asociaciones", asociacionRepositorio.findAll());
        model.addAttribute("competiciones", competicionRepositorio.findAll());
        return "form";
    }

    // ─── GUARDAR CLUB (crear y editar) ───────────────────────────────────────
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Club club, RedirectAttributes ra) {
        clubRepositorio.save(club);
        ra.addFlashAttribute("success", "Club guardado correctamente.");
        return "redirect:/listar";
    }

    // ─── ELIMINAR CLUB ───────────────────────────────────────────────────────
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, RedirectAttributes ra) {
        if (clubRepositorio.existsById(id)) {
            clubRepositorio.deleteById(id);
            ra.addFlashAttribute("success", "Club eliminado correctamente.");
        } else {
            ra.addFlashAttribute("error", "Club no encontrado.");
        }
        return "redirect:/listar";
    }

    // ─── VER JUGADORES DE UN CLUB ────────────────────────────────────────────
    @GetMapping("/club/{id}/jugadores")
    public String verJugadores(@PathVariable String id, Model model, RedirectAttributes ra) {
        Optional<Club> club = clubRepositorio.findById(id);
        if (club.isEmpty()) {
            ra.addFlashAttribute("error", "Club no encontrado.");
            return "redirect:/listar";
        }
        List<Jugador> jugadores = jugadorRepositorio.findByClubId(id);
        model.addAttribute("club", club.get());
        model.addAttribute("jugadores", jugadores);
        return "jugadores";
    }

    // ─── AGREGAR JUGADOR A UN CLUB ───────────────────────────────────────────
    @PostMapping("/club/{clubId}/jugadores/guardar")
    public String guardarJugador(@PathVariable String clubId,
                                  @ModelAttribute Jugador jugador,
                                  RedirectAttributes ra) {
        Optional<Club> club = clubRepositorio.findById(clubId);
        if (club.isEmpty()) {
            ra.addFlashAttribute("error", "Club no encontrado.");
            return "redirect:/listar";
        }
        jugador.setClubId(clubId);
        jugadorRepositorio.save(jugador);
        ra.addFlashAttribute("success", "Jugador agregado correctamente.");
        return "redirect:/club/" + clubId + "/jugadores";
    }
}