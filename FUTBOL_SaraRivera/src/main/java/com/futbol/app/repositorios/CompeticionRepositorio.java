package com.futbol.app.repositorios;

import com.futbol.app.entidades.Competicion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompeticionRepositorio extends MongoRepository<Competicion, String> {
}