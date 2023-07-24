package dev.rogeriofbrito.requesttracker.core.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RequestTrackerElementsTest {

    private static final String URL = "http://localhost:8080";
    private static final String QUERY_STRING = "param1=value1&param2=value2";
    private static final String METHOD = "GET";
    private static final int STATUS = 200;
    private static final String REMOTE_HOST = "0:0:0:0:0:0:0:1";
    private static final int REMOTE_PORT = 52001;
    private static final String PROTOCOL = "http";
    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2000, 1, 13, 0, 0);

    @InjectMocks
    private RequestTrackerElements requestTrackerElements;

    // constructor
    @Test
    void shouldConstruct() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements = new RequestTrackerElements(
                URL,
                QUERY_STRING,
                METHOD,
                STATUS,
                REMOTE_HOST,
                REMOTE_PORT,
                PROTOCOL,
                TIMESTAMP);

        // then
        assertEquals(URL, getValue("url"));
        assertEquals(QUERY_STRING, getValue("queryString"));
        assertEquals(METHOD, getValue("method"));
        assertEquals(STATUS, getValue("status"));
        assertEquals(REMOTE_HOST, getValue("remoteHost"));
        assertEquals(REMOTE_PORT, getValue("remotePort"));
        assertEquals(PROTOCOL, getValue("protocol"));
        assertEquals(TIMESTAMP, getValue("timestamp"));
    }

    // url

    @Test
    void shouldGetUrl() throws NoSuchFieldException, IllegalAccessException {
        //given
        setValue("url", URL);

        //when
        final String url = requestTrackerElements.getUrl();

        //then
        assertEquals(URL, url);
    }

    @Test
    void shouldSetUrl() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements.setUrl(URL);

        //then
        final String url = (String) getValue("url");
        assertEquals(URL, url);
    }

    // method

    @Test
    void shouldGetMethod() throws NoSuchFieldException, IllegalAccessException {
        //given
        setValue("method", METHOD);

        //when
        final String method = requestTrackerElements.getMethod();

        //then
        assertEquals(METHOD, method);
    }

    @Test
    void shouldSetMethod() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements.setMethod(METHOD);

        //then
        final String method = (String) getValue("method");
        assertEquals(METHOD, method);
    }

    // queryString

    @Test
    void shouldGetQueryString() throws NoSuchFieldException, IllegalAccessException {
        //given
        setValue("queryString", QUERY_STRING);

        //when
        final String queryString = requestTrackerElements.getQueryString();

        //then
        assertEquals(QUERY_STRING, queryString);
    }

    @Test
    void shouldSetQueryString() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements.setQueryString(QUERY_STRING);

        //then
        final String queryString = (String) getValue("queryString");
        assertEquals(QUERY_STRING, queryString);
    }

    // status

    @Test
    void shouldGetStatus() throws NoSuchFieldException, IllegalAccessException {
        //given
        setValue("status", STATUS);

        //when
        final int status = requestTrackerElements.getStatus();

        //then
        assertEquals(STATUS, status);
    }

    @Test
    void shouldSetStatus() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements.setStatus(STATUS);

        //then
        final int status = (int) getValue("status");
        assertEquals(STATUS, status);
    }

    // remoteHost

    @Test
    void shouldGetRemoteHost() throws NoSuchFieldException, IllegalAccessException {
        //given
        setValue("remoteHost", REMOTE_HOST);

        //when
        final String remoteHost = requestTrackerElements.getRemoteHost();

        //then
        assertEquals(REMOTE_HOST, remoteHost);
    }

    @Test
    void shouldSetRemoteHost() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements.setRemoteHost(REMOTE_HOST);

        //then
        final String remoteHost = (String) getValue("remoteHost");
        assertEquals(REMOTE_HOST, remoteHost);
    }

    // remotePort

    @Test
    void shouldGetRemotePort() throws NoSuchFieldException, IllegalAccessException {
        //given
        setValue("remotePort", REMOTE_PORT);

        //when
        final int remotePort = requestTrackerElements.getRemotePort();

        //then
        assertEquals(REMOTE_PORT, remotePort);
    }

    @Test
    void shouldSetRemotePort() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements.setRemotePort(REMOTE_PORT);

        //then
        final int remotePort = (int) getValue("remotePort");
        assertEquals(REMOTE_PORT, remotePort);
    }

    // protocol

    @Test
    void shouldGetProtocol() throws NoSuchFieldException, IllegalAccessException {
        //given
        setValue("protocol", PROTOCOL);

        //when
        final String protocol = requestTrackerElements.getProtocol();

        //then
        assertEquals(PROTOCOL, protocol);
    }

    @Test
    void shouldSetProtocol() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements.setProtocol(PROTOCOL);

        //then
        final String protocol = (String) getValue("protocol");
        assertEquals(PROTOCOL, protocol);
    }

    // timestamp

    @Test
    void shouldGetTimestamp() throws NoSuchFieldException, IllegalAccessException {
        //given
        setValue("timestamp", TIMESTAMP);

        //when
        final LocalDateTime timestamp = requestTrackerElements.getTimestamp();

        //then
        assertEquals(TIMESTAMP, timestamp);
    }

    @Test
    void shouldSetTimestamp() throws NoSuchFieldException, IllegalAccessException {
        // when
        requestTrackerElements.setTimestamp(TIMESTAMP);

        //then
        final LocalDateTime timestamp = (LocalDateTime) getValue("timestamp");
        assertEquals(TIMESTAMP, timestamp);
    }

    private Object getValue(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = requestTrackerElements.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(requestTrackerElements);
    }

    private void setValue(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field field = requestTrackerElements.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(requestTrackerElements, value);
    }
}