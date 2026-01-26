package vn.hoidanit.jobhunter.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

public interface FileService {
    void createNewFolder(String folder) throws URISyntaxException;

    String storeFileInFolder(MultipartFile file ,String folder) throws URISyntaxException, IOException;
}
