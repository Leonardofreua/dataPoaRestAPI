package com.dataPoa.dataPoaRestAPI.services.servicesImpl;

import com.dataPoa.dataPoaRestAPI.models.Itinerario;
import com.dataPoa.dataPoaRestAPI.repositories.ItinerarioRepository;
import com.dataPoa.dataPoaRestAPI.repositories.LinhaOnibusRepository;
import com.dataPoa.dataPoaRestAPI.services.ItinerarioService;
import com.dataPoa.dataPoaRestAPI.utils.HttpConnectionConfigureUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ItinerarioServiceImpl implements ItinerarioService {

    private static final String URL_ITINERARIOS = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=";

    private ItinerarioRepository repository;

    private LinhaOnibusRepository linhaOnibusRepository;

    @Autowired
    public ItinerarioServiceImpl(ItinerarioRepository repository, LinhaOnibusRepository linhaOnibusRepository) {
        this.repository = repository;
        this.linhaOnibusRepository = linhaOnibusRepository;
    }

    /**
     * Realiza a requisição para o serviço do DataPoa e retorna uma lista de {@link Itinerario}.
     *
     * @param idLinha id correspondente a linha de ônibus para realização a filtragem dos Itinerários
     * @return {@link List} de {@link Itinerario} correspondente à linha de ônibus
     * @throws IOException
     * @throws JSONException
     */
    public List<Itinerario> getAllItinerariosByLinha(String idLinha) throws IOException, JSONException {
        HttpURLConnection conn = new HttpConnectionConfigureUtil().executeHttpConnection(
                URL_ITINERARIOS + idLinha);
        try {
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                return extractValueToList(content.toString());
            }
        } catch (IOException | JSONException e) {
            throw new JSONException(e.getMessage() + "Linha não encontrada");
        }
        conn.disconnect();
        return new ArrayList<>();
    }

    /**
     * Realização extração dos valores do JSON para uma lista de {@link Itinerario}.
     *
     * @param jsonSring JSON retornado do serviço
     * @return uma lista de {@link Itinerario}
     * @throws JSONException
     */
    private List<Itinerario> extractValueToList(String jsonSring) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonSring);
        List<Itinerario> itinerarios = new ArrayList<>();
        Iterator keys = jsonObject.keys();

        try {
            while (keys.hasNext()) {
                String key = keys.next().toString();
                Itinerario itinerario = new Itinerario();
                if (jsonObject.get(key) instanceof JSONObject) {
                    JSONObject field = jsonObject.getJSONObject(key);
                    itinerario.setToken(Integer.parseInt(key));
                    itinerario.setIdLinha(jsonObject.getString("idlinha"));
                    itinerario.setLat(field.getString("lat"));
                    itinerario.setLng(field.getString("lng"));
                    itinerarios.add(itinerario);
                }
            }
        } catch (JSONException e) {
            throw new JSONException(e.getMessage() + "Linha não encontrada");
        }
        return itinerarios;
    }

    /**
     * Salva a lista de {@link Itinerario} no banco.
     *
     * @param idLinha id referente a linha de ónibus
     * @throws IOException
     * @throws JSONException
     */
    public void saveItinerariosFromService(String idLinha) throws IOException, JSONException {
        List<Itinerario> itinerarios = Optional.ofNullable(this.getAllItinerariosByLinha(idLinha))
                .orElse(new ArrayList<>());
        itinerarios.forEach(itinerario -> repository.save(itinerario));
    }

    /**
     * Salva uma linha de ônibuis.
     *
     * @param itinerario bjeto contendo as características da linha de ônibus a ser salva
     */
    public void save(Itinerario itinerario) {
        repository.save(itinerario);
    }

    /**
     * Busca os itinerários de acordo com o ID da linha de ônibus.
     *
     * @param idLinha ID da linha de ônibus
     * @return lista de {@link Itinerario}
     */
    @Override
    public List<Itinerario> findByIdLinha(String idLinha) {
        return repository.findByIdLinha(idLinha);
    }

    /**
     * Verifica se já existe um {@link Itinerario} com a mesma Latitude ou Longitude cadastrada.
     *
     * @param lat latidade
     * @param lng longitude
     * @return Verdadeiro se já existir um {@link Itinerario} com Latitude ou Longitude cadastrada, Falso de não existir
     */
    @Override
    public boolean existsByIdLinhaAndLatAndLng(String idLinha, String lat, String lng) {
        return repository.existsByIdLinhaAndLatAndLng(idLinha, lat, lng);
    }

    /**
     * Verifica se existe uma {@link com.dataPoa.dataPoaRestAPI.models.LinhaOnibus} com o ID informado.
     *
     * @param idLinha ID da linha
     * @return Verdadeiro se existir uma {@link com.dataPoa.dataPoaRestAPI.models.LinhaOnibus} com o ID informado
     */
    public boolean existsById(String idLinha) {
        return linhaOnibusRepository.existsById(idLinha);
    }

    /**
     * Busca todos os Itinerários cadastrados na base.
     *
     * @return uma {@link List} de {@link Itinerario}
     */
    public List<Itinerario> findAll() {
        return repository.findAll();
    }
}
