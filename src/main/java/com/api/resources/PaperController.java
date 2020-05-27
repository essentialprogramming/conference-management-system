package com.api.resources;

import com.model.Paper;
import com.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class PaperController {

    private PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

//    @POST
//    @Consumes("application/json")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Paper submitPaper(Paper paper) {
//
//        return paperService.submitPaper(paper);
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("paper/{paperId}")
    public Paper findById(@PathParam("paperId") int id) {
        return paperService.findById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("papers")
    public List<Paper> getAll() {

        return paperService.getAll();
    }

    @PUT
    @Path("paper/{paperId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updatePaper(@PathParam("paperId") int paperId, String newContent) {
        paperService.updatePaper(paperId, newContent);
    }


}
