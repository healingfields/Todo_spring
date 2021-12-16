package ma.showmaker.todoclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "todo")
@Data
public class TodoRestClientProperties {
    private String url;
    private String basePath;
}
