package com.dataPoa.dataPoaRestAPI;

import com.dataPoa.dataPoaRestAPI.models.LinhaOnibus;
import com.dataPoa.dataPoaRestAPI.services.servicesImpl.LinhaOnibusServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class LinhaOnibusServiceImplTest {

    @Mock
    private LinhaOnibusServiceImpl service = mock(LinhaOnibusServiceImpl.class);

    @Test
    public void shouldSuccessWhenGetAllBusLineFromService() throws IOException {
        assertTrue(service.getAllLinhasOnibus()
                .stream()
                .anyMatch(Objects::nonNull)
        );

        doThrow(IOException.class)
                .when(service)
                .getAllLinhasOnibus()
                .stream()
                .anyMatch(Objects::isNull);
    }

    @Test
    public void shouldSuccessWhenSaveAllBusLineFromService() throws IOException {
        verify(service, times(1))
                .saveLinhasFromService();

        doThrow(IOException.class)
                .when(service)
                .getAllLinhasOnibus()
                .stream()
                .anyMatch(Objects::isNull);
    }

    @Test
    public void shouldSuccessWhenSaveANewBusLine() {
        LinhaOnibus linhaOnibus = new LinhaOnibus();

        verify(service, times(1))
                .save(linhaOnibus);

        assertNotEquals(null, linhaOnibus);
    }

    @Test(expected = IOException.class)
    public void shouldFailWhenSaveAllBusLineFromService() throws IOException {
        verify(service, times(1))
                .saveLinhasFromService();

        when(service.getAllLinhasOnibus()
                .stream()
                .anyMatch(Objects::isNull))
                .thenThrow(IOException.class);
    }

    @Test(expected = IOException.class)
    public void shouldFailWhenGetAllBusLineFromService() throws IOException {
        when(service.getAllLinhasOnibus()
                .stream()
                .anyMatch(Objects::isNull))
                .thenThrow(IOException.class);
    }
}