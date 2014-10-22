package ch.sbb.conferencebuddy.service.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
public class EtutorCVSReaderTest {

    public static final String TEST_ETUTOR_EXPORT = "/test_etutor_export.csv";

    @Test
    public void testReadUNummer() throws IOException {

        final URL url = this.getClass().getResource(TEST_ETUTOR_EXPORT);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(url.getFile())));

        List<String> uNummern = EtutorCVSReader.readUNummer(bufferedReader);
        bufferedReader.close();


        Assert.assertNotNull(uNummern);
    }
}
