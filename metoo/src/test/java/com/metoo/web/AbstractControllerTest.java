package com.metoo.web;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * User: Zhang xiaomei
 * Date: 2016/11/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@SpringBootTest
public class AbstractControllerTest {

    protected MockMvc mvc;

    public void setup(Object... controllers) throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controllers).build();
    }
}
