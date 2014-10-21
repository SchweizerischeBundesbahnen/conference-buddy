package ch.sbb.conferencebuddy.service.it;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import ch.sbb.esta.util.persistence.test.EstaPersistenceRunner;

/**
 * @author u215246 (Gilles Zimmermann)
 * @version $Id: $
 * @since 2014
 */
@RunWith(EstaPersistenceRunner.class)
@ContextConfiguration({ "classpath:test-esta-persistence.xml",
        "classpath:esta-core-web.xml",
        "classpath:esta-cache.xml",
        "classpath:persistence-context.xml",
        "classpath:root-context.xml",
        "classpath:test-root-context.xml" })
@ActiveProfiles({ "test" })
public class AbstractServiceIT {
}
