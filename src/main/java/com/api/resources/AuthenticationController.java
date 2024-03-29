package com.api.resources;

import com.entities.ApplicationUser;
import com.model.Authentication;
import com.model.Role;
import com.security.TokenUtil;
import com.service.AuthenticationService;
import com.web.json.JsonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Tag(description = "Authorization API", name = "Authorization")
@Path("/")
public class AuthenticationController {

    @Context
    private UriInfo uri;

    @Context
    HttpServletRequest request;

    @Context
    HttpServletResponse response;

    private final AuthenticationService authenticationService;
    private final PasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, PasswordEncoder bCryptPasswordEncoder) {
        this.authenticationService = authenticationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GET
    @Path("authorize")
    @Operation(hidden = true)
    public Response  authorize(@QueryParam("redirect_uri") String redirectUri) throws MalformedURLException {
        // forward user to the login page with the desired redirect_uri as path param
        final String protocol = uri.getBaseUri().toURL().getProtocol();
        final String authority = uri.getBaseUri().getAuthority();
        final URI url = UriComponentsBuilder
                .fromHttpUrl(String.format("%s://%s", protocol, authority) + "/account/sign-in?redirect_uri=" + redirectUri)
                .build()
                .toUri();
        return Response.status(302)
                .header(HttpHeaders.LOCATION, url)
                .build();
    }



    @GET
    @Path("register")
    @Operation(hidden = true)
    public Response register(@QueryParam("redirectUri") String redirectUri) throws MalformedURLException {
        // forward user to the register page with the desired redirect_uri as path param
        final String protocol = uri.getBaseUri().toURL().getProtocol();
        final String authority = uri.getBaseUri().getAuthority();
        final URI url = UriComponentsBuilder
                .fromHttpUrl(String.format("%s://%s", protocol, authority) + "/account/register?redirect_uri=" + redirectUri)
                .build()
                .toUri();
        return Response.status(302)
                .header(HttpHeaders.LOCATION, url)
                .build();
    }


    @POST
    @Path("registerandauthenticate")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse register(@QueryParam("redirect_uri") String redirectUri, Authentication profileInput) {
        authenticationService.register(profileInput);
        return new JsonResponse().with("redirectUrl", redirectUri).done();
    }

    @POST
    @Path("authenticate")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse authenticate(@QueryParam("redirect_uri") String redirectUri, Authentication profileInput) {

        String password = profileInput.getPassword();
        Optional<ApplicationUser> profile = authenticationService.login(profileInput);

        if (profile.isPresent() && bCryptPasswordEncoder.matches(password, profile.get().getPassword())) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, 1);

            boolean isAuthor = false, isPc = false, isChair = false, isParticipant = false;
            for(Role role : profile.get().getRoles())
            {
                if(role==Role.AUTHOR)
                    isAuthor = true;
                if(role==Role.PC_MEMBER)
                    isPc = true;
                if(role==Role.CONFERENCE_CHAIR)
                    isChair = true;
            }

            String token = TokenUtil.createToken("EssentialProgramming", profile.get().getUserName(), isAuthor, isPc, isChair, profile.get().getFirstName(), cal.getTime());
            final String url = redirectUri + "?token=" + token;
            return new JsonResponse()
                    .with("status", "Redirect")
                    .with("redirectUrl", url).done();
        }

        return new JsonResponse()
                .with("status", "Error")
                .with("error", "The username or password you entered is incorrect.").done();
    }


}
