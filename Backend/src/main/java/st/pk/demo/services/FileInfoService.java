package st.pk.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import st.pk.demo.domain.CreatedFileInfoDTO;
import st.pk.demo.domain.FullFileInfoDTO;
import st.pk.demo.model.FileInfo;
import st.pk.demo.repositories.FileInfoRepository;
import st.pk.demo.mappers.FileInfoMapper;

import java.util.List;
import java.util.UUID;

@Service
public class FileInfoService {

    private final FileInfoRepository fileInfoRepository;
    private final FileService fileService;

    public FileInfoService(FileInfoRepository fileInfoRepository, FileService fileService) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileService = fileService;
    }

    public List<FileInfo> getAll() {
        return fileInfoRepository.findAll();
    }

    @Transactional
    public UUID createFile(CreatedFileInfoDTO createdFileInfoDTO) {
        FileInfo fileInfo = FileInfoMapper.fromCreateFileInfoDTO(createdFileInfoDTO);
        fileInfoRepository.save(fileInfo);
        fileService.createFile(fileInfo, createdFileInfoDTO.getContent());
        return fileInfo.getId();
    }

    @Transactional
    public FileInfo updateFile(CreatedFileInfoDTO createdFileInfoDTO, UUID uuid) {
        FileInfo fileInfo = getOneById(uuid);
        FileInfoMapper.copyFromCreatedFileInfoDTOToEntity(createdFileInfoDTO, fileInfo);
        fileInfoRepository.save(fileInfo);
        fileService.updateFile(fileInfo,createdFileInfoDTO.getContent());
        return fileInfo;
    }

    public FileInfo getOneById(UUID id) {
        return fileInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("nie ma takiego pliku"));
    }
}
