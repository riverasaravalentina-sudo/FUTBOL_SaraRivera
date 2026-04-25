package com.futbol.app.controladores;

import com.futbol.app.entidades.Asociacion;
import com.futbol.app.repositorios.AsociacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asociaciones")
public class AsociacionRestControlador {

    @Autowired
    private AsociacionRepositorio asociacionRepositorio;

    // GET /api/asociaciones → listar todas
    @GetMapping
    public List<Asociacion> listar() {
        return asociacionRepositorio.findAll();
    }

    // GET /api/asociaciones/{id} → buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Asociacion> buscarPorId(@PathVariable String id) {
        return asociacionRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/asociaciones → crear nueva
    @PostMapping
    public Asociacion crear(@RequestBody Asociacion asociacion) {
        return asociacionRepositorio.save(asociacion);
    }

    // PUT /api/asociaciones/{id} → actualizar existente
    @PutMapping("/{id}")
    public ResponseEntity<Asociacion> actualizar(@PathVariable String id,
                                                  @RequestBody Asociacion asociacion) {
        if (!asociacionRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        asociacion.setId(id);
        return ResponseEntity.ok(asociacionRepositorio.save(asociacion));
    }

    // DELETE /api/asociaciones/{id} → eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!asociacionRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        asociacionRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}