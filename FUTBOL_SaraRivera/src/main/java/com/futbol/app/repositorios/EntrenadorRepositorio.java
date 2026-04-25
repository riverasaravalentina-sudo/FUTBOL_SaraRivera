package com.futbol.app.repositorios;

import com.futbol.app.entidades.Entrenador;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntrenadorRepositorio extends MongoRepository<Entrenador, String> {
}