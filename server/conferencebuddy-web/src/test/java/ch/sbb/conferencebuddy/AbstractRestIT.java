package ch.sbb.conferencebuddy;

import ch.sbb.conferencebuddy.json.resolver.ObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public abstract class AbstractRestIT {

    /**
     * Die URL von der ersten Seite, kann mit dem System-Property demo.baseUrl (z.B.. via mvn) überschrieben werden
     * (Je nachdem wo die Applikation betrieben wird). Standard-Wert ist für Tomcat konfiguriert.
     */
    protected final String urlOfFirstPage = System.getProperty("demo.baseUrl", "http://localhost:8080/conferencebuddy-web/");

    protected Client client;


    @Before
    public void init(){
        client = ClientBuilder.newBuilder()
            .register(ObjectMapperProvider.class)
            .register(JacksonFeature.class).build();

    }
}
