package info.dmerej.train_reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TrainReservationApplicationTests {
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ApplicationContext context;


    @Test
    void contextLoads() {
    }

    @Test
    void getBooksWhenEmpty() throws Exception {
        var response = mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
        String contents = response.getContentAsString();
        assertThat(contents).isEqualTo("[]");
    }

}
