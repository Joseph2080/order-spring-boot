package org.chitsa.orderservice.services.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminEnableUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.ListUsersRequest;
import com.amazonaws.services.cognitoidp.model.ListUsersResult;
import com.amazonaws.services.cognitoidp.model.MessageActionType;
import com.amazonaws.services.cognitoidp.model.UserType;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import org.chitsa.orderservice.dto.LoginRequestDto;
import org.chitsa.orderservice.dto.UserRegisterDto;
import org.chitsa.orderservice.exception.AuthenticationException;
import org.chitsa.orderservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CognitoUserServiceImpl implements UserService {

    private final AWSCognitoIdentityProvider cognitoClient;

    @Value("${spring.security.oauth2.client.registration.app1.client-id}")
    private String cognitoClientId;

    @Value("${spring.security.oauth2.client.registration.app1.client-secret}")
    private String cognitoClientSecret;

    @Value("${aws.cognito.userPoolId}")
    private String cognitoUserPoolId;

    private static final String ATTRIBUTE_EMAIL = "email";
    private static final String ATTRIBUTE_PHONE_NUMBER = "phone_number";
    private static final String UNIQUE_USERNAME_PREFIX = "user_";
    private static final String SECRET_HASH = "SECRET_HASH";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String ERROR_COGNITO_REQUEST = "Error while processing request to Cognito";
    private static final String ERROR_INVALID_USER_ID = "Invalid user ID.";
    private static final String ERROR_INVALID_CREDENTIALS = "Invalid username or password.";
    private static final String ERROR_PASSWORD_POLICY = "Password does not conform with the password policy.";

    @Autowired
    public CognitoUserServiceImpl(AWSCognitoIdentityProvider cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    @Override
    public String createUser(UserRegisterDto userDto) {
        String uniqueUsername = UNIQUE_USERNAME_PREFIX + UUID.randomUUID();
        try {
            cognitoClient.adminCreateUser(
                    new AdminCreateUserRequest()
                            .withUserPoolId(cognitoUserPoolId)
                            .withUsername(uniqueUsername)
                            .withUserAttributes(
                                    new AttributeType().withName(ATTRIBUTE_EMAIL).withValue(userDto.getEmail()),
                                    new AttributeType().withName(ATTRIBUTE_PHONE_NUMBER).withValue(userDto.getPhoneNumber())
                            )
                            .withTemporaryPassword(userDto.getPassword())
                            .withMessageAction(MessageActionType.SUPPRESS)
            );
            enableUser(uniqueUsername);
            setPassword(uniqueUsername, userDto.getPassword());
            return uniqueUsername;
        } catch (InvalidPasswordException ex) {
            throw new IllegalArgumentException(ERROR_PASSWORD_POLICY, ex);
        } catch (AWSCognitoIdentityProviderException ex) {
            throw new IllegalArgumentException(ERROR_COGNITO_REQUEST, ex);
        }
    }

    private void enableUser(String username) {
        try {
            cognitoClient.adminEnableUser(new AdminEnableUserRequest()
                    .withUserPoolId(cognitoUserPoolId)
                    .withUsername(username));
        } catch (Exception ex) {
            throw new RuntimeException(ERROR_COGNITO_REQUEST, ex);
        }
    }

    private void setPassword(String username, String password) {
        try {
            cognitoClient.adminSetUserPassword(new AdminSetUserPasswordRequest()
                    .withUserPoolId(cognitoUserPoolId)
                    .withUsername(username)
                    .withPassword(password)
                    .withPermanent(true));
        } catch (Exception ex) {
            throw new RuntimeException(ERROR_COGNITO_REQUEST, ex);
        }
    }

    @Override
    public void deleteUser(String username) {
        try {
            cognitoClient.adminDeleteUser(new AdminDeleteUserRequest()
                    .withUserPoolId(cognitoUserPoolId)
                    .withUsername(username));
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(ERROR_INVALID_USER_ID);
        } catch (Exception ex) {
            throw new RuntimeException(ERROR_COGNITO_REQUEST, ex);
        }
    }

    @Override
    public void deleteAllUsers() {
        try {
            ListUsersRequest listUsersRequest = new ListUsersRequest().withUserPoolId(cognitoUserPoolId);
            ListUsersResult listUsersResult;
            do {
                listUsersResult = cognitoClient.listUsers(listUsersRequest);
                for (UserType user : listUsersResult.getUsers()) {
                    deleteUser(user.getUsername());
                }
                listUsersRequest.setPaginationToken(listUsersResult.getPaginationToken());
            } while (listUsersResult.getPaginationToken() != null);
        } catch (Exception ex) {
            throw new RuntimeException(ERROR_COGNITO_REQUEST, ex);
        }
    }

    @Override
    public Map<String, String> loginUser(LoginRequestDto loginRequestDto) {
        try {
            String username = loginRequestDto.getUsername();
            Map<String, String> authParameters = new HashMap<>();
            authParameters.put(USERNAME, username);
            authParameters.put(PASSWORD, loginRequestDto.getPassword());
            authParameters.put(SECRET_HASH,
                    CryptoUtil.calculateSecretHash(
                            cognitoClientId,
                            cognitoClientSecret,
                            username
                    )
            );
            AuthenticationResultType result = cognitoClient.adminInitiateAuth(
                    new AdminInitiateAuthRequest()
                            .withUserPoolId(cognitoUserPoolId)
                            .withClientId(cognitoClientId)
                            .withAuthFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH)
                            .withAuthParameters(authParameters)
            ).getAuthenticationResult();

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", result.getAccessToken());
            response.put("idToken", result.getIdToken());
            response.put("refreshToken", result.getRefreshToken());
            return response;
        } catch (AWSCognitoIdentityProviderException ex) {
            throw new AuthenticationException(ERROR_INVALID_CREDENTIALS, ex);
        }
    }

    @Override
    public boolean doesUserExistsById(String userId) {
        try {
            cognitoClient.adminGetUser(new AdminGetUserRequest()
                    .withUserPoolId(cognitoUserPoolId)
                    .withUsername(userId));
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}
