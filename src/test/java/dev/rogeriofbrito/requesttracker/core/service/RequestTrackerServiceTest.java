package dev.rogeriofbrito.requesttracker.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.rogeriofbrito.requesttracker.core.adapter.RequestTrackerActionAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestTrackerServiceTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse res;
    @Mock
    private Thread sendThread;
    @Mock
    private RequestTrackerActionAdapter recipientAdapter;

    @Spy
    private Clock clock = getClock();
    @Spy
    private ObjectMapper objectMapper = getObjectMapper();

    @InjectMocks
    private RequestTrackerService requestTrackerService;

    @Captor
    private ArgumentCaptor<String> elementsJsonCaptor;

    @Test
    void shouldCallProvider() throws JsonProcessingException {
        // given
        when(recipientAdapter.send(anyString())).thenReturn(sendThread);
        when(req.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080"));
        when(req.getQueryString()).thenReturn("param1=value1&param2=value2");
        when(req.getMethod()).thenReturn("GET");
        when(res.getStatus()).thenReturn(200);
        when(req.getRemoteHost()).thenReturn("0:0:0:0:0:0:0:1");
        when(req.getRemotePort()).thenReturn(52001);
        when(req.getProtocol()).thenReturn("http");

        // when
        requestTrackerService.process(req, res);

        // then
        final String elementsJsonExpected = """
                {"url":"http://localhost:8080","queryString":"param1=value1&param2=value2","method":"GET","status":200,"remoteHost":"0:0:0:0:0:0:0:1","remotePort":52001,"protocol":"http","timestamp":"2000-01-13T00:00:00.000"}""";

        verify(recipientAdapter, times(1)).send(elementsJsonCaptor.capture());
        verify(sendThread, times(1)).start();
        assertEquals(elementsJsonExpected, elementsJsonCaptor.getValue());
    }

    @Test
    void shouldFailWhenObjectMapperThrowsJsonProcessingException() throws JsonProcessingException {
        // given
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        when(req.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080"));
        when(req.getQueryString()).thenReturn("param1=value1&param2=value2");
        when(req.getMethod()).thenReturn("GET");
        when(res.getStatus()).thenReturn(200);
        when(req.getRemoteHost()).thenReturn("0:0:0:0:0:0:0:1");
        when(req.getRemotePort()).thenReturn(52001);
        when(req.getProtocol()).thenReturn("http");

        // when / then
        assertThrows(JsonProcessingException.class, () -> requestTrackerService.process(req, res));
    }

    private Clock getClock() {
        final LocalDate localDate = LocalDate.of(2000, 1, 13);
        return Clock.fixed(
                localDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault());
    }

    private ObjectMapper getObjectMapper() {
        return JsonMapper.builder().findAndAddModules().build();
    }
}