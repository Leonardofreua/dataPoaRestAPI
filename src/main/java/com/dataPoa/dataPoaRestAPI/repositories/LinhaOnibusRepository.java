package com.dataPoa.dataPoaRestAPI.repositories;

import com.dataPoa.dataPoaRestAPI.models.LinhaOnibus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinhaOnibusRepository extends MongoRepository<LinhaOnibus, String> {

    List<LinhaOnibus> findByNome(String nome);

    boolean existsById(String id);

    boolean existsByNomeOrCodigo(String nome, String codigo);
}
