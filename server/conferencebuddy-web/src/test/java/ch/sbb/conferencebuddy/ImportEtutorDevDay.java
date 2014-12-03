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
        importTrack("0", "A", 10, 35);
        importTrack("1", "B", 11, 30);
        importTrack("2", "C", 13, 25);
        importTrack("3", "D", 14, 20);
        importTrack("4", "E", 15, 30);

    }


    private void importTrack( final String staticPartId, final String track, final int hour, final int minutes) throws IOException {
        LOGGER.info("import staticPart {}, track {}", staticPartId, track);
        importCSV("10" + staticPartId, hour, minutes, Resources.toString(Resources.getResource("DevDay/" + track + "1 Präsenzliste.csv"), Charsets.UTF_8));
        importCSV("20" + staticPartId, hour, minutes, Resources.toString(Resources.getResource("DevDay/" + track + "2 Präsenzliste.csv"), Charsets.UTF_8));
        importCSV("30" + staticPartId, hour, minutes, Resources.toString(Resources.getResource("DevDay/" + track + "3 Präsenzliste.csv"), Charsets.UTF_8));
        importCSV("40" + staticPartId, hour, minutes, Resources.toString(Resources.getResource("DevDay/" + track + "4 Präsenzliste.csv"), Charsets.UTF_8));
    }


    private void importCSV(final String pid, final int hour, final int minutes, final String csv){
        final String url = urlOfFirstPage + "admin/" + pid + "/" + hour + "/" + minutes;

        final Response response = client.target(url).request(MediaType.TEXT_PLAIN).post(Entity.text(csv));
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

}
