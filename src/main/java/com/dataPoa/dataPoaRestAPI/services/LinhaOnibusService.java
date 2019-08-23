package com.dataPoa.dataPoaRestAPI.services;

import com.dataPoa.dataPoaRestAPI.models.LinhaOnibus;

import java.util.List;

public interface LinhaOnibusService {

    List<LinhaOnibus> findByNome(String nome);

    boolean existsByNomeOrCodigo(String nome, String codigo);
}
