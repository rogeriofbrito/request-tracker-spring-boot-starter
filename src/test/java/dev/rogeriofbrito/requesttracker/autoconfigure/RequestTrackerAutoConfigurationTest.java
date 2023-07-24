package dev.rogeriofbrito.requesttracker.autoconfigure;

import dev.rogeriofbrito.requesttracker.core.service.RequestTrackerService;
import dev.rogeriofbrito.requesttracker.filter.RequestTrackerFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;

@ExtendWith(MockitoExtension.class)
class RequestTrackerAutoConfigurationTest {

    @Mock
    private RequestTrackerService requestTrackerService;

    @InjectMocks
    private RequestTrackerAutoConfiguration requestTrackerAutoConfiguration;

    @Test
    void shouldCreateRequestTrackerService() {
        // given
        try (final MockedConstruction<RequestTrackerService> mocked = mockConstruction(
                RequestTrackerService.class,
                (mock, context) -> {
                })) {

            // when
            final RequestTrackerService requestTrackerService = requestTrackerAutoConfiguration.requestTrackerService();

            // then
            assertEquals(mocked.constructed().size(), 1);
            assertEquals(mocked.constructed().get(0), requestTrackerService);
        }
    }

    @Test
    void shouldCreateRequestTrackerFilter() {
        // given
        try (final MockedConstruction<RequestTrackerFilter> mocked = mockConstruction(
                RequestTrackerFilter.class,
                (mock, context) -> {
                })) {

            // when
            final RequestTrackerFilter requestTrackerFilter = requestTrackerAutoConfiguration.requestTrackerFilter(
                    requestTrackerService);

            // then
            assertEquals(mocked.constructed().size(), 1);
            assertEquals(mocked.constructed().get(0), requestTrackerFilter);
        }
    }

    @Test
    void shouldSpringImportsFileValid() throws IOException {
        final InputStream is = this.getClass().getClassLoader().getResourceAsStream("META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports");
        final String content = new String(Objects.requireNonNull(is).readAllBytes(), StandardCharsets.UTF_8);

        assertEquals(RequestTrackerAutoConfiguration.class.getCanonicalName(), content);
    }

    @Test
    void shouldSpringFactoriesFileValid() throws IOException {
        final InputStream is = this.getClass().getClassLoader().getResourceAsStream("META-INF/spring.factories");
        final String content = new String(Objects.requireNonNull(is).readAllBytes(), StandardCharsets.UTF_8);

        final String expectedContent = String.format("""
                org.springframework.boot.autoconfigure.EnableAutoConfiguration=\\
                  %s""", RequestTrackerAutoConfiguration.class.getCanonicalName());

        assertEquals(expectedContent, content);
    }
}
