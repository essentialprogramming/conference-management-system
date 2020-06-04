package com.api.resources;

import com.entities.ApplicationUser;
import com.entities.RoleEntity;
import com.model.Authentication;
import com.model.Role;
import com.security.TokenUtil;
import com.service.AuthenticationService;
import com.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/")
public class AuthenticationController {

    @Context
    private UriInfo uri;

    @Autowired
    private ServletContext context;

    private AuthenticationService authenticationService;
    private PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GET
    @Path("authorize")
    public Response  authorize(@QueryParam("redirectUri") String redirectUri) throws UnsupportedEncodingException {
        // forward user to the login page with the desired redirect_uri as path param
        final URI url = UriComponentsBuilder
                .fromHttpUrl(uri.getBaseUri() + "login?redirect_uri=" + redirectUri)
                .build()
                .toUri();
        return Response.status(302)
                .header(HttpHeaders.LOCATION, url)
                .build();
    }

    @GET
    @Path("login")
    public Response authenticate(@QueryParam("redirect_uri") String redirect_uri) {
        InputStream resource = context.getResourceAsStream("login.html");
        return null == resource ? Response.status(NOT_FOUND).build() : Response.ok().entity(resource).build();
    }


    @GET
    @Path("register")
    public Response register(@QueryParam("redirectUri") String redirectUri) throws UnsupportedEncodingException {
        // forward user to the login page with the desired redirect_uri as path param
        final URI url = UriComponentsBuilder
                .fromHttpUrl(uri.getBaseUri() + "registeraccount?redirect_uri=" + redirectUri)
                .build()
                .toUri();
        return Response.status(302)
                .header(HttpHeaders.LOCATION, url)
                .build();
    }

    @GET
    @Path("registeraccount")
    public Response registerAccount(@QueryParam("redirect_uri") String redirect_uri) {
        InputStream resource = context.getResourceAsStream("index.html");
        return null == resource ? Response.status(NOT_FOUND).build() : Response.ok().entity(resource).build();
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
            for(RoleEntity role : profile.get().getRoles())
            {
                if(role.getRole()==Role.AUTHOR)
                    isAuthor = true;
                if(role.getRole()==Role.PC_MEMBER)
                    isPc = true;
                if(role.getRole()==Role.CONFERENCE_CHAIR)
                    isChair = true;
            }

            String token = TokenUtil.createToken("EssentialProgramming", profile.get().getUserName(), isAuthor, isPc, isChair, profile.get().getFirstName(), cal.getTime());
            final String url = redirectUri + "?token=" + token;
            return new JsonResponse().with("redirectUrl", url).done();
        }

        return new JsonResponse()
                .with("status", "Error")
                .with("error", "The username or password you entered is incorrect.").done();
    }


}
