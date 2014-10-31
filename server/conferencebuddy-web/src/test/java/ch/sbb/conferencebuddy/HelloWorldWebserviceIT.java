package ch.sbb.conferencebuddy;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public class HelloWorldWebserviceIT extends AbstractRestIT {


    @Test
    public void testHelloWorld() {
        final String url = urlOfFirstPage + "helloworld";
        final Response response = client.target(url).request("text/plain").get();
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
    }

}
