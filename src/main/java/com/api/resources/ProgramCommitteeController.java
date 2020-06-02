package com.api.resources;

import com.model.*;
import com.output.EvaluationJSON;
import com.output.PaperJSON;
import com.service.ProgramCommitteeService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/programCommittee")
public class ProgramCommitteeController {

    private ProgramCommitteeService pcService;
    private UserService userService;

    @Autowired
    public ProgramCommitteeController(ProgramCommitteeService pcService, UserService userService) {
        this.pcService = pcService;
        this.userService = userService;
    }


    @POST
    @Path("/bid/{paperId}/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void bidProposal(@PathParam("paperId") int paperId, @PathParam("email") String email, Status status) {
        userService.bidProposal(paperId, email, status);
    }

    @PUT
    @Path("/user/paper/{paperId}/{email}")
    public String assignPaper(@PathParam("paperId") int paperId, @PathParam("email") String email) {
        return pcService.assignPaper(paperId, email);
    }

    @PUT
    @Path("/paper/section/{paperId}/{sectionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PaperJSON setPaperSection(@PathParam("paperId") int paperId, @PathParam("sectionId") int sectionId) {
        return pcService.setPaperSection(paperId, sectionId);
    }

    @PUT
    @Path("user/review/{paperId}/{email}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public EvaluationJSON reviewPaper(@PathParam("paperId") int paperId, @PathParam("email") String email, EvaluationInput evaluationInput) {

        return pcService.reviewPaper(paperId, email, evaluationInput);
    }

}
