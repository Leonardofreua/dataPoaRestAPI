package com.dataPoa.dataPoaRestAPI.controllers;

import com.dataPoa.dataPoaRestAPI.models.Itinerario;
import com.dataPoa.dataPoaRestAPI.services.servicesImpl.ItinerarioServiceImpl;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ItinerarioController {

    private ItinerarioServiceImpl service;

    @Autowired
    public ItinerarioController(ItinerarioServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/itinerarios/get-from-service/{idLinha}")
    public List<Itinerario> getItinerarioByLinha(@PathVariable("idLinha") String idLinha) throws IOException,
            JSONException {
        return service.getAllItinerariosByLinha(idLinha);
    }

    @GetMapping("/itinerarios/linha/{idLinha}")
    public List<Itinerario> findByIdLinha(@PathVariable("idLinha") String idLinha) {
        return service.findByIdLinha(idLinha);
    }

    @GetMapping("/itinerarios/list")
    public List<Itinerario> findAll() {
        return service.findAll();
    }

    @PostMapping("/itinerarios/post-from-service/{idLinha}")
    public void saveItinerariosFromService(@PathVariable String idLinha) throws IOException, JSONException {
        service.saveItinerariosFromService(idLinha);
    }

    @PostMapping("/itinerario/new")
    public String save(@RequestBody Itinerario itinerario) {
        if (service.existsByIdLinhaAndLatAndLng(itinerario.getIdLinha(), itinerario.getLat(), itinerario.getLng())) {
            return "Já existe uma Linha de Ônibus cadastrada com este Itinerário!";
        } else if (!service.existsById(itinerario.getIdLinha())) {
            return "Não foi encontrada nenhuma Linha de Ônibus com este ID.";
        } else {
            service.save(itinerario);
            return "Itinerário cadastrado com sucesso!";
        }
    }
}
