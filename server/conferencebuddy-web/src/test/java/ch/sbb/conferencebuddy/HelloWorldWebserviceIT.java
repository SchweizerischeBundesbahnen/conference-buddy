package ch.sbb.conferencebuddy;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public class HelloWorldWebserviceIT extends AbstractRestIT {


    @Test
    public void testInsertUserTalks() {
        final String url = urlOfFirstPage + "/helloworld";
        final Response response = client.target(url).request("text/plain").post(Entity.text("hans"));
        Assert.assertNotNull(response);
    }

}
