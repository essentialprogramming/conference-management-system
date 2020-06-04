package com.service;
import com.entities.PaperEntity;
import com.repository.PaperRepository;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;

@Service
public class UploadService
{
    private PaperRepository paperRepository;
    private static final String FILE_PATH = "C:\\Papers\\";

    @Autowired
    public UploadService(PaperRepository paperRepository)
    {
        this.paperRepository = paperRepository;
    }

    @Transactional
    public void upload(InputStream uploadedInputStream, FormDataContentDisposition fileDetails, int paperId)
    {
        //destination folder: "C:\Papers\"
        String uploadedFileLocation = FILE_PATH + fileDetails.getFileName();
        writeToFile(uploadedInputStream, uploadedFileLocation);
        PaperEntity paperEntity = paperRepository.findById(paperId).orElseThrow(() -> new RuntimeException("Paper does not exist!\n"));
        if(paperEntity.getFileName() != null)
        {
            File file = new File(FILE_PATH + paperEntity.getFileName());
            file.delete();
        }
        paperEntity.setFileName(fileDetails.getFileName());
    }

    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {
        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
