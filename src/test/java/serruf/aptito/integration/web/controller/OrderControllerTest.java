package serruf.aptito.integration.web.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:test.properties")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @ExpectedDatabase(value = "order/create.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldCreateOrder() throws Exception {
        mockMvc.perform(post("/order")
                .content("{\"name\": \"test\", \"amount\": 666}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DatabaseSetup("order/update/setup.xml")
    @ExpectedDatabase(value = "order/update/decline.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldUpdateNotPayedOrder() throws Exception {
        mockMvc.perform(patch("/order/1")
                .content("{\"name\": \"new_name\", \"amount\": 100}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    @DatabaseSetup("order/update/setup.xml")
    @ExpectedDatabase(value = "order/update/complete.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldUpdateAndCompleteOrder() throws Exception
    {
        mockMvc.perform(patch("/order/2")
                .content("{\"name\": \"new_name\", \"amount\": 100}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    @DatabaseSetup("order/update/setup.xml")
    public void shouldReturnBadRequest() throws Exception {
        mockMvc.perform(patch("/order/3")
                .content("{\"name\": \"new_name\", \"amount\": 100}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}
