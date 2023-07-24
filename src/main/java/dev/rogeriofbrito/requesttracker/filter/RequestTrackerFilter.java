package dev.rogeriofbrito.requesttracker.filter;

import dev.rogeriofbrito.requesttracker.core.service.RequestTrackerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestTrackerFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTrackerFilter.class);

    private final RequestTrackerService requestTrackerService;

    public RequestTrackerFilter(RequestTrackerService requestTrackerService) {
        this.requestTrackerService = requestTrackerService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.debug("executing request tracker filter");

        try {
            requestTrackerService.process(request, response);
        } catch (Exception e) {
            logger.error("an error occurred when process Request Tracker Service: {}", e.getMessage());
            logger.debug("error", e);
        }

        filterChain.doFilter(request, response);
    }
}
