package st.pk.demo.mappers;

import org.springframework.beans.factory.annotation.Value;
import st.pk.demo.domain.CreatedFileInfoDTO;
import st.pk.demo.domain.FileInfoGridDTO;
import st.pk.demo.domain.FullFileInfoDTO;
import st.pk.demo.model.FileInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@SuppressWarnings("Duplicates")
public class FileInfoMapper {

    @Value("${filesPath}")
    private static String filesPath;

    public static FileInfo fromCreateFileInfoDTO(CreatedFileInfoDTO createdFileInfoDTO) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(createdFileInfoDTO.getName());
        fileInfo.setDescription(createdFileInfoDTO.getDescription());
        fileInfo.setVersion(createdFileInfoDTO.getVersion());
        return fileInfo;
    }

    public static FileInfoGridDTO fromEntityToFileInfoGridDTO(FileInfo fileInfo) {
        FileInfoGridDTO fileInfoGridDTO = new FileInfoGridDTO();
        fileInfoGridDTO.setIsEditing(fileInfo.getIsEditing());
        fileInfoGridDTO.setCreatedBy(fileInfo.getCreatedBy());
        fileInfoGridDTO.setCreatedDate(new Date(fileInfo.getCreatedDate()));
        fileInfoGridDTO.setModifiedBy(fileInfo.getModifiedBy());
        fileInfoGridDTO.setModifiedDate(new Date(fileInfo.getModifiedDate()));
        fileInfoGridDTO.setId(fileInfo.getId());
        fileInfoGridDTO.setName(fileInfo.getName());
        fileInfoGridDTO.setVersion(fileInfo.getVersion());
        fileInfoGridDTO.setDescription(fileInfo.getDescription());
        String filePath = "/files" + "/" + fileInfo.getId().toString();
        fileInfoGridDTO.setSize(String.format("%.2f", new File(filePath).length() / 1024.0) + " KB");
        try {
            List<String> list = Files.readAllLines(Paths.get(filePath));
            final String[] content = {""};
            list.forEach(s -> content[0] = content[0] + s + "\n");
            fileInfoGridDTO.setContent(content[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileInfoGridDTO.setEditedBy(fileInfo.getEditedBy());
        return fileInfoGridDTO;
    }

    public static FullFileInfoDTO fromEntityToFullFileInfoDTO(FileInfo fileInfo) {
        FullFileInfoDTO fullFileInfoDTO = new FullFileInfoDTO();
        fullFileInfoDTO.setIsEditing(fileInfo.getIsEditing());
        fullFileInfoDTO.setCreatedBy(fileInfo.getCreatedBy());
        fullFileInfoDTO.setCreatedDate(new Date(fileInfo.getCreatedDate()));
        fullFileInfoDTO.setModifiedBy(fileInfo.getModifiedBy());
        fullFileInfoDTO.setModifiedDate(new Date(fileInfo.getModifiedDate()));
        fullFileInfoDTO.setId(fileInfo.getId());
        fullFileInfoDTO.setName(fileInfo.getName());
        fullFileInfoDTO.setVersion(fileInfo.getVersion());
        fullFileInfoDTO.setDescription(fileInfo.getDescription());
        String filePath = "/files" + "/" + fileInfo.getId().toString();
        fullFileInfoDTO.setSize(String.format("%.2f", new File(filePath).length() / 1024.0) + " KB");
        try {
            List<String> list = Files.readAllLines(Paths.get(filePath));
            final String[] content = {""};
            list.forEach(s -> content[0] = content[0] + s + "\n");
            fullFileInfoDTO.setContent(content[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fullFileInfoDTO.setEditedBy(fileInfo.getEditedBy());
        return fullFileInfoDTO;
    }

    public static void copyFromCreatedFileInfoDTOToEntity(CreatedFileInfoDTO dto, FileInfo fileInfo) {
        fileInfo.setName(dto.getName());
        fileInfo.setDescription(dto.getDescription());
    }
}
