package ru.glosav.gais.gateway;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class GatewayApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();


    @Test
	public void contextLoads() {
	}

    @Before
    public void init() {
        mockMvc = webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }


}
