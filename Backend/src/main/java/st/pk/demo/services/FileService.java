package st.pk.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import st.pk.demo.model.FileInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Service
public class FileService {

    @Value("${filesPath}")
    private String filesPath;

    public void createFile(FileInfo fileInfo, String content) {
        if (!Files.exists(Paths.get(filesPath))) {
            try {
                Files.createDirectory(Paths.get(filesPath));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Nie mozna stworzyc folderu");
            }
        }
        String filePath = filesPath + "/" + fileInfo.getId().toString();
        System.out.println(filePath);
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("Nie mozna zapisac pliku");
        }
    }

    public void updateFile(FileInfo fileInfo, String content) {
        File file = new File(filesPath + "/" + fileInfo.getId().toString());
        file.delete();
        this.createFile(fileInfo, content);
    }
}
