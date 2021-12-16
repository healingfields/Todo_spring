package ma.showmaker.todoclient.validation;

import ma.showmaker.todoclient.domain.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.Charset;

public class TodoErrorHandler extends DefaultResponseErrorHandler {

    private Logger LOG = LoggerFactory.getLogger(TodoErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        LOG.error(response.getStatusCode().toString());
        LOG.error(StreamUtils.copyToString(
                response.getBody(), Charset.defaultCharset()
        ));
    }
}

