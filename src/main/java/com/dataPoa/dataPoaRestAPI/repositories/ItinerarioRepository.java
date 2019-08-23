package com.dataPoa.dataPoaRestAPI.repositories;

import com.dataPoa.dataPoaRestAPI.models.Itinerario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItinerarioRepository extends MongoRepository<Itinerario, String> {

    List<Itinerario> findByIdLinha(String idLinha);

    boolean existsByIdLinhaAndLatAndLng(String idLinha, String lat, String lng);
}
