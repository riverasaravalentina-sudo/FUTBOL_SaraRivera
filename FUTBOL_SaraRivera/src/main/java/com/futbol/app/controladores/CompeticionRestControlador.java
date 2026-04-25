package com.futbol.app.controladores;

import com.futbol.app.entidades.Competicion;
import com.futbol.app.repositorios.CompeticionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competiciones")
public class CompeticionRestControlador {

    @Autowired
    private CompeticionRepositorio competicionRepositorio;

    // GET /api/competiciones → listar todas
    @GetMapping
    public List<Competicion> listar() {
        return competicionRepositorio.findAll();
    }

    // GET /api/competiciones/{id} → buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Competicion> buscarPorId(@PathVariable String id) {
        return competicionRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/competiciones → crear nueva
    @PostMapping
    public Competicion crear(@RequestBody Competicion competicion) {
        return competicionRepositorio.save(competicion);
    }

    // PUT /api/competiciones/{id} → actualizar existente
    @PutMapping("/{id}")
    public ResponseEntity<Competicion> actualizar(@PathVariable String id,
                                                   @RequestBody Competicion competicion) {
        if (!competicionRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        competicion.setId(id);
        return ResponseEntity.ok(competicionRepositorio.save(competicion));
    }

    // DELETE /api/competiciones/{id} → eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!competicionRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        competicionRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}