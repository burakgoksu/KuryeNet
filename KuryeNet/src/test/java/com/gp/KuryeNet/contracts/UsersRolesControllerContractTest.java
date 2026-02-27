package com.gp.KuryeNet.contracts;

import static org.mockito.Mockito.when;
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

import com.gp.KuryeNet.core.business.abstracts.UserRoleService;
import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.entities.UserRole;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;

@SpringBootTest
@AutoConfigureMockMvc
class UsersRolesControllerContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRoleService userRoleService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getDeleted_shouldReturnUserRoleDtos() throws Exception {
        User user = new User();
        user.setId(10);
        Role role = new Role();
        role.setRoleId(3);
        UserRole userRole = new UserRole();
        userRole.setUserRoleId(7);
        userRole.setUser(user);
        userRole.setRole(role);

        when(userRoleService.getDeleted()).thenReturn(new SuccessDataResult<>(List.of(userRole)));

        mockMvc.perform(get("/api/usersroles/deleted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].userRoleId").value(7))
                .andExpect(jsonPath("$.data[0].userId").value(10))
                .andExpect(jsonPath("$.data[0].roleId").value(3));
    }
}
