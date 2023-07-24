package dev.rogeriofbrito.requesttracker.filter;

import dev.rogeriofbrito.requesttracker.core.service.RequestTrackerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestTrackerFilterTest {

    @Mock
    private RequestTrackerService requestTrackerService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse res;
    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private RequestTrackerFilter requestTrackerFilter;

    @Captor
    private ArgumentCaptor<HttpServletRequest> reqCaptor;
    @Captor
    private ArgumentCaptor<HttpServletResponse> resCaptor;

    @Test
    void shouldDoFilterInternal() throws ServletException, IOException {
        // when
        requestTrackerFilter.doFilterInternal(req, res, filterChain);

        // then
        verify(requestTrackerService, times(1)).process(reqCaptor.capture(), resCaptor.capture());
        verify(filterChain, times(1)).doFilter(reqCaptor.capture(), resCaptor.capture());

        assertEquals(req, reqCaptor.getAllValues().get(0));
        assertEquals(req, reqCaptor.getAllValues().get(1));
        assertEquals(res, resCaptor.getAllValues().get(0));
        assertEquals(res, resCaptor.getAllValues().get(1));
    }

    @Test
    void shouldIgnoreExceptionInProcess() throws IOException, ServletException {
        // given
        doThrow(RuntimeException.class)
                .when(requestTrackerService).process(any(HttpServletRequest.class), any(HttpServletResponse.class));

        // when
        requestTrackerFilter.doFilterInternal(req, res, filterChain);

        // then
        verify(filterChain, times(1)).doFilter(reqCaptor.capture(), resCaptor.capture());

        assertEquals(req, reqCaptor.getAllValues().get(0));
        assertEquals(res, resCaptor.getAllValues().get(0));
    }

    @Test
    void shouldFailWhenDoFilterThrowsServletException() throws ServletException, IOException {
        // given
        doThrow(ServletException.class)
                .when(filterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));

        // when / then
        assertThrows(ServletException.class, () -> requestTrackerFilter.doFilterInternal(req, res, filterChain));
    }

    @Test
    void shouldFailWhenDoFilterThrowsIOException() throws ServletException, IOException {
        // given
        doThrow(IOException.class)
                .when(filterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));

        // when / then
        assertThrows(IOException.class, () -> requestTrackerFilter.doFilterInternal(req, res, filterChain));
    }
}