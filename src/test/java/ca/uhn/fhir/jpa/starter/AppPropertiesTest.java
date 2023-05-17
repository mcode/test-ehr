package ca.uhn.fhir.jpa.starter;

import junit.framework.TestCase;
import org.hl7.davinci.ehrserver.ClientAuthorizationInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AppPropertiesTest extends TestCase {
@Autowired
AppProperties props;

    @Test
    public void testGetClientAuthorizationInterceptor() {
        assertNotNull(props);
        ClientAuthorizationInterceptor cai = props.getClientAuthorizationInterceptor();
        assertNotNull(cai);
    }


}