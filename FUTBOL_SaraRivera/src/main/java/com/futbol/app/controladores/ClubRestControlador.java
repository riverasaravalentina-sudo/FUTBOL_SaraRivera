package com.futbol.app.controladores;

import com.futbol.app.entidades.Club;
import com.futbol.app.repositorios.ClubRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubes")
public class ClubRestControlador {

    @Autowired
    private ClubRepositorio clubRepositorio;

    // GET /api/clubes → listar todos
    @GetMapping
    public List<Club> listar() {
        return clubRepositorio.findAll();
    }

    // GET /api/clubes/{id} → buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Club> buscarPorId(@PathVariable String id) {
        return clubRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/clubes → crear nuevo
    @PostMapping
    public Club crear(@RequestBody Club club) {
        return clubRepositorio.save(club);
    }

    // PUT /api/clubes/{id} → actualizar existente
    @PutMapping("/{id}")
    public ResponseEntity<Club> actualizar(@PathVariable String id, @RequestBody Club club) {
        if (!clubRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        club.setId(id);
        return ResponseEntity.ok(clubRepositorio.save(club));
    }

    // DELETE /api/clubes/{id} → eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!clubRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clubRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}