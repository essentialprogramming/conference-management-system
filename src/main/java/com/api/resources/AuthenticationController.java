package com.api.resources;

import com.entities.ProfileEntity;
import com.model.ProfileInput;
import com.service.AuthenticateService;
import com.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class AuthenticationController {

    private AuthenticateService authenticationService;
    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthenticationController(AuthenticateService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @POST
    @Path("authenticate")
    @Consumes("application/json")
    public void register(ProfileInput profileInput) {
        authenticationService.register(profileInput);
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public JsonResponse login(ProfileInput profileInput) {

        String password = profileInput.getPassword();
        ProfileEntity profile = authenticationService.login(profileInput);

        if (bCryptPasswordEncoder.matches(password, profile.getPassword())) {
            return new JsonResponse().with("status", "ok");
        }

        return new JsonResponse().with("status", "The username or password you entered is incorrect.");
    }


}
