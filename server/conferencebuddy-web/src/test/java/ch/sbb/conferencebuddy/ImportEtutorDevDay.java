package ch.sbb.conferencebuddy;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Gilles Zimmermann
 * @version $Id: $
 * @since 2014
 */
public class ImportEtutorDevDay extends AbstractRestIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportEtutorTech4.class);

    @Test
    public void importAllTrack() throws IOException {
        LOGGER.info(urlOfFirstPage);
        importTrack( "A", 10, 30);
        importTrack( "B", 11, 20);
        importTrack( "C", 13, 10);
        importTrack( "D", 14, 00);
        importTrack( "E", 15, 10);
    }


    private void importTrack(final String track, final int hour, final int minutes) throws IOException {
        LOGGER.info("import staticPart {}, track {}", track);
        importCSV(track + "1", hour, minutes, Resources.toString(Resources.getResource("DevDay/" + track + "1.csv"), Charsets.UTF_8));
        importCSV(track + "2", hour, minutes, Resources.toString(Resources.getResource("DevDay/" + track + "2.csv"), Charsets.UTF_8));
        importCSV(track + "3", hour, minutes, Resources.toString(Resources.getResource("DevDay/" + track + "3.csv"), Charsets.UTF_8));
        importCSV(track + "4", hour, minutes, Resources.toString(Resources.getResource("DevDay/" + track + "4.csv"), Charsets.UTF_8));
    }


    private void importCSV(final String pid, final int hour, final int minutes, final String csv){
        final String url = urlOfFirstPage + "admin/" + pid + "/" + hour + "/" + minutes;

        final Response response = client.target(url).request(MediaType.TEXT_PLAIN).post(Entity.text(csv));
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

}
