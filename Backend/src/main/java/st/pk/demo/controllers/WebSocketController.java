package st.pk.demo.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import st.pk.demo.domain.BlockFileDTO;
import st.pk.demo.domain.Message;
import st.pk.demo.services.FileInfoService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate template;
    private final FileInfoService fileInfoService;
    private final Gson g;

    @Autowired
    public WebSocketController(SimpMessagingTemplate template, FileInfoService fileInfoService) {
        this.template = template;
        this.fileInfoService = fileInfoService;
        g = new Gson();
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(String text) {
        Message message = g.fromJson(text, Message.class);
        message.setTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        this.template.convertAndSend("/chat", message);
    }

    @MessageMapping("/file/create")
    public void onFileCreate(String message) {
        this.template.convertAndSend("/newFile", "refresh");
    }

    @MessageMapping("/file/block")
    public void onBlockFile(String text) {
        BlockFileDTO blockFileDTO = g.fromJson(text, BlockFileDTO.class);
        this.fileInfoService.blockFile(blockFileDTO);
        this.template.convertAndSend("/blockFile", blockFileDTO);
    }

    @MessageMapping("/file/unblock")
    public void onUnblockFile(String id) {
        String uuid = g.fromJson(id, String.class);
        this.fileInfoService.unblockFile(UUID.fromString(uuid));
        this.template.convertAndSend("/unblockFile", uuid);
    }


}
