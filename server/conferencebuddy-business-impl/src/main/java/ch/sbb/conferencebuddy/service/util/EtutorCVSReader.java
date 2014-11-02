package ch.sbb.conferencebuddy.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public class EtutorCVSReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtutorCVSReader.class);

    private static final String CVS_SPLIT_BY = ";";

    public static List<String> readUNummer(BufferedReader reader) throws IOException {
        final List<String> uNummern = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {

            // use comma as separator
            String[] record = line.split(CVS_SPLIT_BY);
            if(record.length > 0 && record[0].toUpperCase().startsWith("U")){
                uNummern.add(record[0]);
            }
            else{
                LOGGER.info("Line doesn't start with an 'u-nummer': {}", line);
            }
        }

        return uNummern;
    }
}
