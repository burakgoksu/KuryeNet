package com.gp.KuryeNet.contracts;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.gp.KuryeNet.business.abstracts.CustomerService;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.entities.concretes.Customer;

@SpringBootTest
@AutoConfigureMockMvc
class CustomersControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void delete_shouldBeForbiddenWithoutAdmin() throws Exception {
        mockMvc.perform(delete("/api/customers/delete")
                        .param("customerId", "1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_shouldSucceedForAdmin() throws Exception {
        when(customerService.delete(1)).thenReturn(new SuccessResult("customer deleted"));

        mockMvc.perform(delete("/api/customers/delete")
                        .param("customerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("customer deleted"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getDeleted_shouldReturnDtos() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(2);
        customer.setCustomerName("Ali");
        customer.setCustomerSurname("Demir");
        customer.setCustomerEmail("alidemir1@gmail.com");

        when(customerService.getDeleted()).thenReturn(new SuccessDataResult<>(List.of(customer)));

        mockMvc.perform(get("/api/customers/deleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].customerId").value(2))
                .andExpect(jsonPath("$.data[0].customerEmail").value("alidemir1@gmail.com"));
    }
}
