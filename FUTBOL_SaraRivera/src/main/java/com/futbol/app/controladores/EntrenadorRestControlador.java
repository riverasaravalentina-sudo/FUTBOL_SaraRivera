package com.futbol.app.controladores;

import com.futbol.app.entidades.Entrenador;
import com.futbol.app.repositorios.EntrenadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorRestControlador {

    @Autowired
    private EntrenadorRepositorio entrenadorRepositorio;

    // GET /api/entrenadores → listar todos
    @GetMapping
    public List<Entrenador> listar() {
        return entrenadorRepositorio.findAll();
    }

    // GET /api/entrenadores/{id} → buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Entrenador> buscarPorId(@PathVariable String id) {
        return entrenadorRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/entrenadores → crear nuevo
    @PostMapping
    public Entrenador crear(@RequestBody Entrenador entrenador) {
        return entrenadorRepositorio.save(entrenador);
    }

    // PUT /api/entrenadores/{id} → actualizar existente
    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> actualizar(@PathVariable String id,
                                                  @RequestBody Entrenador entrenador) {
        if (!entrenadorRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entrenador.setId(id);
        return ResponseEntity.ok(entrenadorRepositorio.save(entrenador));
    }

    // DELETE /api/entrenadores/{id} → eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!entrenadorRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entrenadorRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
