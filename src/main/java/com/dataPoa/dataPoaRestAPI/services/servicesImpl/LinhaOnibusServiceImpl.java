package com.dataPoa.dataPoaRestAPI.services.servicesImpl;

import com.dataPoa.dataPoaRestAPI.models.LinhaOnibus;
import com.dataPoa.dataPoaRestAPI.repositories.LinhaOnibusRepository;
import com.dataPoa.dataPoaRestAPI.services.LinhaOnibusService;
import com.dataPoa.dataPoaRestAPI.utils.HttpConnectionConfigureUtil;
import com.dataPoa.dataPoaRestAPI.utils.converters.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaOnibusServiceImpl implements LinhaOnibusService {

    private static final String URL_LINHAS_ONIBUS = "http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o";

    private LinhaOnibusRepository repository;

    @Autowired
    public LinhaOnibusServiceImpl(LinhaOnibusRepository repository) {
        this.repository = repository;
    }

    /**
     * Realiza a requisição para o serviço do DataPoa e retorna uma lista de {@link LinhaOnibus}.
     *
     * @return {@link List} de {@link LinhaOnibus}
     * @throws IOException
     */
    public List<LinhaOnibus> getAllLinhasOnibus() throws IOException {
        HttpURLConnection conn = new HttpConnectionConfigureUtil().executeHttpConnection(URL_LINHAS_ONIBUS);

        try {
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                return new JsonConverter<LinhaOnibus>().convertJsonToArrayList(content.toString(), LinhaOnibus.class);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        conn.disconnect();
        return new ArrayList<>();
    }

    /**
     * Salva a lista de {@link LinhaOnibus} no banco.
     *
     * @throws IOException
     */
    public void saveLinhasFromService() throws IOException {
        List<LinhaOnibus> linhas = Optional.ofNullable(this.getAllLinhasOnibus())
                .orElse(new ArrayList<>());
        linhas.forEach(linha -> repository.save(linha));
    }

    /**
     * Salva uma linha de ônibuis.
     *
     * @param linhaOnibus objeto contendo as características da linha de ônibus a ser salva
     */
    public void save(LinhaOnibus linhaOnibus) {
        repository.save(linhaOnibus);
    }

    /**
     * Realiza a busca de {@link LinhaOnibus} por nome.
     *
     * @param nome da linha de Ônibus
     * @return uma {@link List} contendo os dados referente a linha informada
     */
    @Override
    public List<LinhaOnibus> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    /**
     * Verifica se existe uma {@link LinhaOnibus} por meio do nome OU código (prefixo).
     *
     * @param nome da linha
     * @param codigo prefixo da linha
     * @return Verdadeiro se existir uma linha cadastrada com o Nome ou Código informado, Falso se não houver nenhuma
     * linha com estás características
     */
    @Override
    public boolean existsByNomeOrCodigo(String nome, String codigo) {
        return repository.existsByNomeOrCodigo(nome, codigo);
    }

    /**
     * Busca todas as Linhas de Ônibus cadastradas na base.
     *
     * @return uma {@link List} de {@link LinhaOnibus}
     */
    public List<LinhaOnibus> findAll() {
        return repository.findAll();
    }
}

