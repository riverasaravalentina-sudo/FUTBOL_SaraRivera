package com.futbol.app.repositorios;

import com.futbol.app.entidades.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JugadorRepositorio extends MongoRepository<Jugador, String> {
    List<Jugador> findByClubId(String clubId);
}