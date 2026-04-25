package com.futbol.app.repositorios;

import com.futbol.app.entidades.Asociacion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AsociacionRepositorio extends MongoRepository<Asociacion, String> {
}