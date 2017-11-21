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
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
public class ImportEtutorTech4 extends AbstractRestIT {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImportEtutorTech4.class);

  @Test
  public void importAllTrack() throws IOException {
    LOGGER.info(urlOfFirstPage);
    importTrack("A");
    importTrack("B");
    importTrack("C");
    importTrack("D");
    importTrack("W");
  }


  private void importTrack(final String track) throws IOException {
    LOGGER.info("importing track {}", track);
    importCSV("1" + track, 10, 50, Resources.toString(Resources.getResource("tech4/1" + track + ".csv"), Charsets.UTF_8));
    importCSV("2" + track, 11, 50, Resources.toString(Resources.getResource("tech4/2" + track + ".csv"), Charsets.UTF_8));
    importCSV("3" + track, 15, 05, Resources.toString(Resources.getResource("tech4/3" + track + ".csv"), Charsets.UTF_8));
    importCSV("4" + track, 16, 05, Resources.toString(Resources.getResource("tech4/4" + track + ".csv"), Charsets.UTF_8));
  }


  private void importCSV(final String pid, final int hour, final int minutes, final String csv) {
    LOGGER.info("importing pid {}", pid);
    final String url = urlOfFirstPage + "admin/" + pid + "/" + hour + "/" + minutes;
    final Response response = client.target(url).request(MediaType.TEXT_PLAIN).post(Entity.text(csv));
    Assert.assertNotNull(response);
    Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
  }

}
