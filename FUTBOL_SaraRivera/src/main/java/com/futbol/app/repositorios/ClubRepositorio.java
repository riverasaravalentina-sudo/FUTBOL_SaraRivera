package com.futbol.app.repositorios;

import com.futbol.app.entidades.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClubRepositorio extends MongoRepository<Club, String> {
}