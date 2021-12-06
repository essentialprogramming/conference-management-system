package com.service;
import com.entities.PaperEntity;
import com.repository.PaperRepository;
import com.util.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class UploadService {

    public static final String FILE_PATH = FileUtils.getResourceBasePath() + File.separator +  "papers";


    private final PaperRepository paperRepository;


    @Autowired
    public UploadService(PaperRepository paperRepository){
        this.paperRepository = paperRepository;
    }

    @Transactional
    public void upload(InputStream uploadedInputStream, FormDataContentDisposition fileDetails, int paperId) {

        String uploadedFileLocation = FILE_PATH +  File.separator + fileDetails.getFileName();
        writeToFile(uploadedInputStream, uploadedFileLocation);
        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new RuntimeException("Paper does not exist!\n"));
        if(paperEntity.getFileName() != null)
        {
            File file = new File(FILE_PATH + paperEntity.getFileName());
            file.delete();
        }
        paperEntity.setFileName(fileDetails.getFileName());
    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        try {
            Path targetFile = FileUtils.getPath(uploadedFileLocation, true);
            Files.copy(uploadedInputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);

            uploadedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
