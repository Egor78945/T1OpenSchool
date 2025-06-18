package com.example.transaction_service.service.security.user.authentication;

import com.example.transaction_service.exception.AuthenticationException;
import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.client.status.entity.ClientStatus;
import com.example.transaction_service.model.role.entity.Role;
import com.example.transaction_service.model.role.enumeration.RoleEnumeration;
import com.example.transaction_service.model.user.dto.UserCredentialDTO;
import com.example.transaction_service.model.user.dto.UserDetailsDTO;
import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.model.user.role.entity.UserRole;
import com.example.transaction_service.service.client.authentication.AbstractClientAuthenticationService;
import com.example.transaction_service.service.client.status.ClientStatusService;
import com.example.transaction_service.service.common.security.token.TokenService;
import com.example.transaction_service.service.security.role.RoleService;
import com.example.transaction_service.service.security.user.UserService;
import com.example.transaction_service.service.security.user.role.UserRoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserAuthenticationServiceTest {
    @Mock
    private UserService<User> userService;
    @Mock
    private UserRoleService<UserRole> userRoleService;
    @Mock
    private ClientStatusService<ClientStatus> clientStatusService;
    @Mock
    private RoleService<Role> roleService;
    @Mock
    private AbstractClientAuthenticationService<Client> clientAuthenticationService;
    @Mock
    private TokenService<String, UserCredentialDTO> tokenService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    private static String email;
    private static String password;
    private static String passwordHash;
    private static UserDetailsDTO registrationModel;
    private static User rightRegisteringUser;
    private static User wrongRegisteringUser;

    @BeforeAll
    public static void initCredentials(){
        email = "email123@gmail.com";
        password = "password12345";
        passwordHash = new BCryptPasswordEncoder().encode(password);
        registrationModel = new UserDetailsDTO(email, password, "name", "surname", "patronymic");
        rightRegisteringUser = new User(email, passwordHash);
        wrongRegisteringUser = new User(email, "wrong password");
    }

    @Test
    public void login_wrongPassword_throwsAuthenticationException(){
        Mockito.when(userService.getByEmail(email)).thenReturn(wrongRegisteringUser);

        Assertions.assertThrows(IllegalArgumentException.class, () -> userAuthenticationService.login(email, password));
    }

    @Test
    public void login_rightPassword_returnsAuthenticationToken() {
        Mockito.when(userService.getByEmail(email)).thenReturn(rightRegisteringUser);
        Mockito.when(userRoleService.getAllByUserEmail(email)).thenReturn(null);
        Mockito.when(tokenService.generateToken(Mockito.any())).thenReturn("token");

        Assertions.assertEquals("token", userAuthenticationService.login(email, password));
    }

    @Test
    public void registration_userAlreadyExists_throwsProcessingException(){
        Mockito.when(userService.existsByEmail(registrationModel.getEmail().toLowerCase())).thenReturn(true);

        Assertions.assertThrows(ProcessingException.class, () -> userAuthenticationService.registration(registrationModel));
    }

    @Test
    public void registration_successfullyRegistration_returnsRegisteredUserDetailsDTO(){
        Mockito.when(userService.existsByEmail(registrationModel.getEmail().toLowerCase())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(registrationModel.getPassword())).thenReturn(passwordHash);
        Mockito.when(userService.save(Mockito.any())).thenReturn(rightRegisteringUser);
        Mockito.when(clientAuthenticationService.registration(Mockito.any())).thenReturn(null);
        Mockito.when(roleService.getById(RoleEnumeration.ROLE_USER.getId())).thenReturn(new Role(RoleEnumeration.ROLE_USER.name()));
        Mockito.doNothing().when(userRoleService).save(Mockito.any());

        Assertions.assertEquals(registrationModel, userAuthenticationService.registration(registrationModel));
    }
}
