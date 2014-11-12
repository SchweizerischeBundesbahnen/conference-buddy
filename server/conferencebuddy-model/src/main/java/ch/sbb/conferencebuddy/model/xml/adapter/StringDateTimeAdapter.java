package ch.sbb.conferencebuddy.model.xml.adapter;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
public class StringDateTimeAdapter extends XmlAdapter<String, DateTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DateTime unmarshal(final String v) {
        return new DateTime(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String marshal(final DateTime v) {
        return v.toString();
    }
}