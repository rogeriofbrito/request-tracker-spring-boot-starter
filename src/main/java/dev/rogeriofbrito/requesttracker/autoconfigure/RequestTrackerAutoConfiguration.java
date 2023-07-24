package dev.rogeriofbrito.requesttracker.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.rogeriofbrito.requesttracker.core.adapter.RequestTrackerActionAdapter;
import dev.rogeriofbrito.requesttracker.core.service.RequestTrackerService;
import dev.rogeriofbrito.requesttracker.filter.RequestTrackerFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@ConditionalOnBean(RequestTrackerActionAdapter.class)
public class RequestTrackerAutoConfiguration {

    final RequestTrackerActionAdapter requestTrackerActionAdapter;

    public RequestTrackerAutoConfiguration(RequestTrackerActionAdapter requestTrackerActionAdapter) {
        this.requestTrackerActionAdapter = requestTrackerActionAdapter;
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestTrackerService requestTrackerService() {
        final Clock clock = Clock.systemDefaultZone();
        final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
        return new RequestTrackerService(requestTrackerActionAdapter, clock, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestTrackerFilter requestTrackerFilter(RequestTrackerService requestTrackerService) {
        return new RequestTrackerFilter(requestTrackerService);
    }
}
