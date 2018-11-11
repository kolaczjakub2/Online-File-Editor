package st.pk.demo.controllers;

import org.springframework.web.bind.annotation.*;
import st.pk.demo.domain.CreatedFileInfoDTO;
import st.pk.demo.domain.FileInfoGridDTO;
import st.pk.demo.domain.FullFileInfoDTO;
import st.pk.demo.mappers.FileInfoMapper;
import st.pk.demo.services.FileInfoService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "files")
public class FileInfoController {


    private final FileInfoService fileInfoService;
    private final WebSocketController webSocketController;
    private final List<String> users = new ArrayList<>();

    public FileInfoController(final FileInfoService fileInfoService, WebSocketController webSocketController) {
        this.fileInfoService = fileInfoService;
        this.webSocketController = webSocketController;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    List<FileInfoGridDTO> getAllFiles() {
        return fileInfoService.getAll().stream().map(FileInfoMapper::fromEntityToFileInfoGridDTO).collect(Collectors.toList());
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    UUID createFile(@RequestBody CreatedFileInfoDTO createdFileInfoDTO) {
        UUID id = fileInfoService.createFile(createdFileInfoDTO);
        webSocketController.onFileCreate("");
        return id;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    FullFileInfoDTO updateFile(@RequestBody CreatedFileInfoDTO createdFileInfoDTO, @PathVariable String id) {
        webSocketController.onFileCreate(id);
        return FileInfoMapper.fromEntityToFullFileInfoDTO(fileInfoService.updateFile(createdFileInfoDTO, UUID.fromString(id)));
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    FullFileInfoDTO getFile(@PathVariable String id) {
        return FileInfoMapper.fromEntityToFullFileInfoDTO(fileInfoService.getOneById(UUID.fromString(id)));
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/chat/{name}")
    void addToChatList(@PathVariable String name) {
        users.add(name);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/chat")
    List<String> getChatUsers() {
        return users;
    }

}
