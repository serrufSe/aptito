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
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DatabaseSetup("transaction/pay/setup.xml")
    @ExpectedDatabase(value = "transaction/pay/part.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldPayOrderPart() throws Exception {
        mockMvc.perform(post("/order/1/pay")
                .content("{\"amount\": 100}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    @DatabaseSetup("transaction/pay/setup.xml")
    @ExpectedDatabase(value = "transaction/pay/complete.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void shouldCompleteOrder() throws Exception {
        mockMvc.perform(post("/order/2/pay")
                .content("{\"amount\": 200}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    @DatabaseSetup("transaction/pay/setup.xml")
    public void shouldRerunBadRequest() throws Exception {
        mockMvc.perform(post("/order/2/pay")
                .content("{\"amount\": 300}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}
