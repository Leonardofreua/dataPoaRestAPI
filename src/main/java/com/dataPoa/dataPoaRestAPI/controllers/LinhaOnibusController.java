package com.dataPoa.dataPoaRestAPI.controllers;

import com.dataPoa.dataPoaRestAPI.models.LinhaOnibus;
import com.dataPoa.dataPoaRestAPI.services.servicesImpl.LinhaOnibusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class LinhaOnibusController {

    private LinhaOnibusServiceImpl service;

    @Autowired
    public LinhaOnibusController(LinhaOnibusServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/linhas/get-from-service")
    public List<LinhaOnibus> getAll() throws IOException {
        return service.getAllLinhasOnibus();
    }

    @GetMapping("/linha/{nome}/**")
    public List<LinhaOnibus> findByNome(@PathVariable("nome") String nome, HttpServletRequest request) {
        final String path =
                request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        final String bestMatchingPattern =
                request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();

        String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);
        String nomeLinha;

        if (null != arguments && !arguments.isEmpty()) {
            nomeLinha = nome + '/' + arguments;
        } else {
            nomeLinha = nome;
        }

        return service.findByNome(nomeLinha);
    }

    @GetMapping("/linhas/list")
    public List<LinhaOnibus> findAll() {
        return service.findAll();
    }

    @PostMapping("/linhas/post-from-service")
    public void saveLinhasFromService() throws IOException {
        service.saveLinhasFromService();
    }

    @PostMapping("/linha/new")
    public String save(@RequestBody LinhaOnibus linhaOnibus) {
        if (service.existsByNomeOrCodigo(linhaOnibus.getNome(), linhaOnibus.getCodigo())) {
            return "Já existe uma Linha de Ônibus com este Nome ou Código (Prefixo)!";
        } else {
            service.save(linhaOnibus);
            return "Linha de Ônibus cadastrada com sucesso!";
        }
    }
}
