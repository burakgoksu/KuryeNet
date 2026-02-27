package com.gp.KuryeNet.contracts;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.gp.KuryeNet.business.abstracts.OrderService;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.entities.concretes.Order;

@SpringBootTest
@AutoConfigureMockMvc
class OrdersControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getByOrderNumber_shouldBeForbiddenWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/orders/getByOrderNumber")
                        .param("orderNumber", "ys-123"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getByOrderNumber_shouldAllowCustomer() throws Exception {
        Order order = buildOrder();
        when(orderService.getByOrderNumber("ys-123"))
                .thenReturn(new SuccessDataResult<>(order));

        mockMvc.perform(get("/api/orders/getByOrderNumber")
                        .param("orderNumber", "ys-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.orderNumber").value("ys-123"));
    }

    @Test
    @WithMockUser(roles = "COURIER")
    void getByOrderNumber_v1_shouldAllowCourier() throws Exception {
        Order order = buildOrder();
        when(orderService.getByOrderNumber("ys-123"))
                .thenReturn(new SuccessDataResult<>(order));

        mockMvc.perform(get("/api/v1/orders/getByOrderNumber")
                        .param("orderNumber", "ys-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.orderNumber").value("ys-123"));
    }

    private Order buildOrder() {
        Order order = new Order();
        order.setOrderId(1);
        order.setOrderNumber("ys-123");
        order.setOrderType("standard");
        order.setOrderDate(new Date());
        order.setEstimatedDeliveryTime(new Date());
        order.setOrderStatus(1);
        return order;
    }
}
