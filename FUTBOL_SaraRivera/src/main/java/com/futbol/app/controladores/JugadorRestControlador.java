package com.futbol.app.controladores;

import com.futbol.app.entidades.Jugador;
import com.futbol.app.repositorios.JugadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorRestControlador {

    @Autowired
    private JugadorRepositorio jugadorRepositorio;

    // GET /api/jugadores → listar todos
    @GetMapping
    public List<Jugador> listar() {
        return jugadorRepositorio.findAll();
    }

    // GET /api/jugadores/{id} → buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Jugador> buscarPorId(@PathVariable String id) {
        return jugadorRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/jugadores/club/{clubId} → jugadores por club
    @GetMapping("/club/{clubId}")
    public List<Jugador> listarPorClub(@PathVariable String clubId) {
        return jugadorRepositorio.findByClubId(clubId);
    }

    // POST /api/jugadores → crear nuevo
    @PostMapping
    public Jugador crear(@RequestBody Jugador jugador) {
        return jugadorRepositorio.save(jugador);
    }

    // PUT /api/jugadores/{id} → actualizar existente
    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizar(@PathVariable String id,
                                               @RequestBody Jugador jugador) {
        if (!jugadorRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jugador.setId(id);
        return ResponseEntity.ok(jugadorRepositorio.save(jugador));
    }

    // DELETE /api/jugadores/{id} → eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!jugadorRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jugadorRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
