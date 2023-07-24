package dev.rogeriofbrito.requesttracker.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rogeriofbrito.requesttracker.core.adapter.RequestTrackerActionAdapter;
import dev.rogeriofbrito.requesttracker.core.domain.RequestTrackerElements;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Clock;
import java.time.LocalDateTime;

public class RequestTrackerService {

    private static final Logger logger = LogManager.getLogger(RequestTrackerService.class);
    private final RequestTrackerActionAdapter requestTrackerActionAdapter;
    private final Clock clock;
    private final ObjectMapper objectMapper;

    public RequestTrackerService(RequestTrackerActionAdapter requestTrackerActionAdapter,
                                 Clock clock, ObjectMapper objectMapper) {
        this.requestTrackerActionAdapter = requestTrackerActionAdapter;
        this.clock = clock;
        this.objectMapper = objectMapper;
    }

    public void process(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        logger.debug("executing request tracker service");

        final RequestTrackerElements requestTrackerElements = buildRequestData(request, response);
        final String requestTrackerElementsJson = objectMapper.writeValueAsString(requestTrackerElements);
        final Thread actionThread = requestTrackerActionAdapter.send(requestTrackerElementsJson);

        actionThread.start();
    }

    private RequestTrackerElements buildRequestData(HttpServletRequest request, HttpServletResponse response) {
        final RequestTrackerElements requestTrackerElements = new RequestTrackerElements();
        requestTrackerElements.setUrl(request.getRequestURL().toString());
        requestTrackerElements.setQueryString(request.getQueryString());
        requestTrackerElements.setMethod(request.getMethod());
        requestTrackerElements.setStatus(response.getStatus());
        requestTrackerElements.setRemoteHost(request.getRemoteHost());
        requestTrackerElements.setRemotePort(request.getRemotePort());
        requestTrackerElements.setProtocol(request.getProtocol());
        requestTrackerElements.setTimestamp(LocalDateTime.now(clock));

        return requestTrackerElements;
    }
}
