package com.api.resources;

import com.model.Paper;
import com.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/paper")
public class PaperController {

    private ProposalService proposalService;

    @Autowired
    public PaperController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Paper submitPaper(Paper paper) {

        return proposalService.submitPaper(paper);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Paper findById(@PathParam("id") int id) {
        return proposalService.findById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paper> getAll() {
        return proposalService.getAll();
    }

    @PUT
    @Path("/{paperId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updatePaper(@PathParam("paperId") int paperId, String newContent) {
        proposalService.updatePaper(paperId, newContent);
    }


}
