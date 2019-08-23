package com.dataPoa.dataPoaRestAPI;

import com.dataPoa.dataPoaRestAPI.models.Itinerario;
import com.dataPoa.dataPoaRestAPI.services.servicesImpl.ItinerarioServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.json.JSONException;
import java.io.IOException;
import java.util.Objects;
import java.lang.String;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ItinerarioServiceImplTest {

    @Mock
    private ItinerarioServiceImpl service = mock(ItinerarioServiceImpl.class);
    
    private String idLinha = "5566";

    @Test
    public void shouldSuccessWhenGetAllItinerariosFromService() throws IOException, JSONException {
        assertTrue(service.getAllItinerariosByLinha(this.idLinha)
                .stream()
                .anyMatch(Objects::nonNull)
        );
        
        doThrow(IOException.class)
                .when(service)
                .getAllItinerariosByLinha(this.idLinha)
                .stream()
                .anyMatch(Objects::isNull);
    }

    @Test
    public void shouldSuccessWhenSaveItinerariosFromService() throws IOException, JSONException {
        verify(service, times(1))
                .saveItinerariosFromService(this.idLinha);
    }

    @Test
    public void shouldSuccessWhenSaveANewItinerario() {
        Itinerario itinerario = new Itinerario();

        verify(service, times(1))
                .save(itinerario);

        assertNotEquals(null, itinerario);
    }

    @Test
    public void shouldFailWhenSaveItinerariosFromService() throws IOException, JSONException {
        verify(service, times(1))
                .saveItinerariosFromService(this.idLinha);

        when(service.getAllItinerariosByLinha(this.idLinha)
                .stream()
                .anyMatch(Objects::isNull))
                .thenThrow(IOException.class);
    }

    @Test
    public void shouldFailWhenGetAllItinerariosFromService() throws IOException, JSONException {
        when(service.getAllItinerariosByLinha(this.idLinha)
                .stream()
                .anyMatch(Objects::isNull))
                .thenThrow(IOException.class);
    }
}
