package com.api.resources;

import com.model.*;
import com.service.ProgramCommitteeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/programCommittee")
public class ProgramCommitteeController {

    private ProgramCommitteeService pcService;

    @Autowired
    public ProgramCommitteeController(ProgramCommitteeService pcService) {
        this.pcService = pcService;
    }

//    @POST
//    @Path("/recommendation")
//    @Consumes("application/json")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Recommendation addRecommendation(Recommendation recommendation) {
//        return pcService.addRecommendation(recommendation);
//    }


//    @PUT
//    @Path("/user/{email}")
//    @Consumes("application/json")
//    @Produces(MediaType.APPLICATION_JSON)
//    public void updateUserRole(@PathParam("email") String email, Role role) {
//
//        pcService.updateUserRole(email, role);
//    }

    @GET
    @Path("/user/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUserByEmail(@PathParam("email") String email) {
        return pcService.findUserByEmail(email);
    }

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
    public Paper setPaperSection(@PathParam("paperId") int paperId, @PathParam("sectionId") int sectionId) {
        return pcService.setPaperSection(paperId, sectionId);
    }

    @PUT
    @Path("user/review/{email}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Evaluation reviewPaper(@PathParam("email") String email, Evaluation evaluation) {

        return pcService.reviewPaper(email, evaluation);
    }

}
