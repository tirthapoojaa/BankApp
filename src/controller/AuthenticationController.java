package controller;

import model.User;
import service.AuthenticationService;

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public User login(String userId, String password) {
        return authenticationService.login(userId, password);
    }
}
