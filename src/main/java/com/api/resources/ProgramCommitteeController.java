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
    public ProgramCommitteeController(ProgramCommitteeService pcService) {
        this.pcService = pcService;
    }


    @GET
    @Path("/bid/{paperId}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public void bidProposal(@PathParam("paperId") int paperId, @PathParam("email") String email, Status status) {
        userService.bidProposal(paperId, email, status);
    }
//    @POST
//    @Path("/recommendation")
//    @Consumes("application/json")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Recommendation addRecommendation(Recommendation recommendation) {
//        return pcService.addRecommendation(recommendation);
//    }


    @PUT
    @Path("/user/paper/{paperId}/{email}")
    public String assignPaper(@PathParam("paperId") int paperId, @PathParam("email") String email) {
        return pcService.assignPaper(paperId, email);
    }

//    @PUT
//    @Path("user/review/{paperId}/{email}")
//    @Consumes("application/json")
//    public String reviewPaper(@PathParam("paperId") int paperId, @PathParam("email") String email, Qualifier qualifier, @QueryParam("text") String recommendation) {
//        return pcService.reviewPaper(paperId, email, qualifier, recommendation);
//    }

//    @PUT
//    @Path("section/supervisor/{sectionId}/{email}")
//    @Consumes("application/json")
//    public void updateSectionSupervisor(@PathParam("sectionId") int sectionId, @PathParam("email") String email) {
//        pcService.updateSectionSupervisor(sectionId, email);
//    }

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
