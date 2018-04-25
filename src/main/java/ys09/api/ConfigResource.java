package ys09.api;

import com.google.gson.Gson;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.WriterRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.conf.Configuration;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ConfigResource extends ServerResource {

    @Override
    protected Representation get() throws ResourceException {

        return new WriterRepresentation(MediaType.APPLICATION_JSON) {

            @Override
            public void write(Writer writer) throws IOException {
                Map<String, String> map = new HashMap<>();
                Configuration config = Configuration.getInstance();
                for (String key : Configuration.CONFIG_KEYS) {
                    map.put(key, config.getProperty(key));
                }
                Gson gson = new Gson();
                writer.write(gson.toJson(map));
            }

        };
    }
}
