package st.pk.demo.InitialHelpers;

import org.springframework.stereotype.Component;
import st.pk.demo.model.FileInfo;
import st.pk.demo.repositories.FileInfoRepository;
import st.pk.demo.services.FileService;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class InitialService {

    private final FileInfoRepository fileInfoRepository;
    private final FileService fileService;

    public InitialService(FileInfoRepository fileInfoRepository, FileService fileService) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileService = fileService;
    }

    //@PostConstruct
    private void init() {
        for (int i = 0; i < 20; i++) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setName("Pliki z serwera " + i);
            fileInfo.setDescription("j/w");
            fileInfo.setVersion(0L);
            fileInfo.setIsEditing(i % 2 == 0);
            fileInfo = fileInfoRepository.save(fileInfo);
            fileService.createFile(fileInfo, "Nie wiem" + i);
        }
    }
}
