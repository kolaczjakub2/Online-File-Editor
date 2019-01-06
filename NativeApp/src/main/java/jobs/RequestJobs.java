package jobs;


import Domain.CreatedFileInfoDTO;
import Domain.FullFileInfoDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import service.FileInfoService;
import service.FileService;

import java.io.IOException;
import java.util.*;

public class RequestJobs extends TimerTask {
    final static FileInfoService fileInfoService = new FileInfoService();
    final static FileService fileService = new FileService();

    private static final String url = "http://localhost:8080/files";
    public static List<UUID> filesToCreate = new ArrayList<>();
    public static List<UUID> filesToUpdate = new ArrayList<>();

    public RequestJobs() {
        filesToCreate = fileService.readIds("create");
        filesToUpdate = fileService.readIds("update");
    }

    public void run() {
        if (!FileInfoService.offline) {
            filesToCreate.forEach(uuid -> {
                FullFileInfoDTO file = fileService.getOne(uuid);
                CreatedFileInfoDTO createdFile = new CreatedFileInfoDTO();
                createdFile.setContent(file.getContent());
                createdFile.setName(file.getName());
                createdFile.setDescription(file.getDescription());
                fileInfoService.addFile(createdFile);
                System.out.println("wyslano plik");
            });
            filesToCreate.clear();
            filesToUpdate.forEach(uuid -> {
                FullFileInfoDTO file = fileService.getOne(uuid);
                CreatedFileInfoDTO createdFile = new CreatedFileInfoDTO();
                createdFile.setContent(file.getContent());
                createdFile.setName(file.getName());
                createdFile.setDescription(file.getDescription());
                createdFile.setVersion(file.getVersion());
                fileInfoService.editFile(createdFile, file.getId());
                System.out.println("update pliku plik");
            });
            filesToUpdate.clear();
            fileInfoService.getAllFiles();
            fileService.deleteFile("create");
            fileService.deleteFile("update");
        } else {
            System.out.println("nie ma polaczenia z serwerem");
        }

    }
}
