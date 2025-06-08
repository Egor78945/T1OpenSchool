package com.example.transaction_service.service.security.user.authentication;

import com.example.transaction_service.exception.AuthenticationException;
import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.client.entity.Client;
import com.example.transaction_service.model.role.entity.Role;
import com.example.transaction_service.model.role.enumeration.RoleEnumeration;
import com.example.transaction_service.model.user.dto.UserCredentialDTO;
import com.example.transaction_service.model.user.dto.UserDetailsDTO;
import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.model.user.role.entity.UserRole;
import com.example.transaction_service.service.client.authentication.AbstractClientAuthenticationService;
import com.example.transaction_service.service.common.authentication.AbstractAuthenticationService;
import com.example.transaction_service.service.common.security.token.TokenService;
import com.example.transaction_service.service.security.role.RoleService;
import com.example.transaction_service.service.security.user.UserService;
import com.example.transaction_service.service.security.user.role.UserRoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService extends AbstractAuthenticationService<UserDetailsDTO> {
    private final UserService<User> userService;
    private final UserRoleService<UserRole> userRoleService;
    private final RoleService<Role> roleService;
    private final AbstractClientAuthenticationService<Client> clientAuthenticationService;
    private final TokenService<String, UserCredentialDTO> tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserAuthenticationService(@Qualifier("userServiceManager") UserService<User> userService, @Qualifier("userRoleServiceManager") UserRoleService<UserRole> userRoleService, @Qualifier("roleServiceManager") RoleService<Role> roleService, @Qualifier("clientAuthenticationServiceManager") AbstractClientAuthenticationService<Client> clientAuthenticationService, @Qualifier("JWTService") TokenService<String, UserCredentialDTO> tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.clientAuthenticationService = clientAuthenticationService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String email, String password) {
        if(BCrypt.checkpw(password, userService.getByEmail(email.toLowerCase()).getPassword())){
            UserCredentialDTO userCredentialDTO = new UserCredentialDTO(userService.getByEmail(email), userRoleService.getAllByUserEmail(email));
            return tokenService.generateToken(userCredentialDTO);
        } else {
            throw new AuthenticationException("authentication error");
        }
    }

    @Override
    public UserDetailsDTO registration(UserDetailsDTO registrationModel) {
        if (!userService.existsByEmail(registrationModel.getEmail().toLowerCase())) {
            User user = userService.save(new User(registrationModel.getEmail().toLowerCase(), passwordEncoder.encode(registrationModel.getPassword())));
            clientAuthenticationService.registration(new Client(registrationModel.getName(), registrationModel.getSurname(), registrationModel.getPatronymic(), user));
            UserRole userRole = new UserRole(user, roleService.getById(RoleEnumeration.ROLE_USER.getId()));
            userRoleService.save(userRole);
            return registrationModel;
        } else {
            throw new ProcessingException("user is already exists");
        }
    }
}
