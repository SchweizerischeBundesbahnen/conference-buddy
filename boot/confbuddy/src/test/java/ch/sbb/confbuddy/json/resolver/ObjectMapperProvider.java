package ch.sbb.confbuddy.json.resolver;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    final ObjectMapper defaultObjectMapper;

    public ObjectMapperProvider() {
        this.defaultObjectMapper = getObjectMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING, true)
                .setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
    }
}
