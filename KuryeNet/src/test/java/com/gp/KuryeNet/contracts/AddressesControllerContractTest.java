package com.gp.KuryeNet.contracts;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.gp.KuryeNet.business.abstracts.AddressService;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.entities.concretes.Address;

@SpringBootTest
@AutoConfigureMockMvc
class AddressesControllerContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_shouldReturnAddressDtos() throws Exception {
        Address address = buildAddress();

        when(addressService.getAll()).thenReturn(new SuccessDataResult<>(List.of(address)));

        MvcResult result = mockMvc.perform(get("/api/addresses/getall"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].addressId").value(1))
                .andExpect(jsonPath("$.data[0].city").value("Manisa"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("05458624532"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_v1_shouldReturnAddressDtos() throws Exception {
        Address address = buildAddress();

        when(addressService.getAll()).thenReturn(new SuccessDataResult<>(List.of(address)));

        MvcResult result = mockMvc.perform(get("/api/v1/addresses/getall"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].addressId").value(1))
                .andExpect(jsonPath("$.data[0].city").value("Manisa"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("05458624532"));
    }

    private Address buildAddress() {
        Address address = new Address();
        address.setAddressId(1);
        address.setAddress("Ataturk Mahallesi");
        address.setAddressTitle("Home");
        address.setCity("Manisa");
        address.setDistrict("Yunus Emre");
        address.setStreet("Muradiye");
        address.setBuildingNumber("2");
        address.setFloorNumber("5");
        address.setApartmentNumber("12");
        address.setPhoneNumber("05458624532");
        return address;
    }
}
