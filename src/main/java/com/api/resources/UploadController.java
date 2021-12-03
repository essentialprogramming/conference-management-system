package com.api.resources;

import com.service.UploadService;
import com.web.json.JsonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Tag(description = "Paper API", name = "Paper Services")
@Path("/")
public class UploadController {

    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService)
    {
        this.uploadService = uploadService;
    }

    @POST
    @Path("upload/{paperId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse uploadPaper(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetails,
            @PathParam("paperId") int paperId) {

        //System.out.println(fileDetails.getFileName());

        this.uploadService.upload(uploadedInputStream, fileDetails, paperId);

        //String output = "File uploaded to : " + uploadedFileLocation;
        //System.out.println(output);
        return new JsonResponse().with("status", "Success").done();
    }
}
