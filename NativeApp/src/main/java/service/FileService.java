package service;

import Domain.FileInfoGridDTO;
import Domain.FullFileInfoDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.lang.reflect.Type;

public class FileService {

    private final String filesPath = "/app";

    public void createFile(UUID fileInfoId, String content) {
        if (!Files.exists(Paths.get(filesPath))) {
            try {
                Files.createDirectory(Paths.get(filesPath));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Nie mozna stworzyc folderu");
            }
        }
        String filePath = filesPath + "/" + fileInfoId.toString();
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

    public void updateFile(UUID fileInfoId, String content) {
        File file = new File(filesPath + "/" + fileInfoId.toString());
        file.delete();
        this.createFile(fileInfoId, content);
    }

    public void saveMetadata(FileInfoGridDTO file) {
        boolean isEditing = file.getIsEditing() != null ? file.getIsEditing() : false;
        file.setIsEditing(false);
        try (Writer writer = new FileWriter("/app/" + file.getId() + ".json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(file, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        file.setIsEditing(isEditing);
    }

    public FullFileInfoDTO getOne(UUID id) {
        Type REVIEW_TYPE = new TypeToken<FullFileInfoDTO>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("/app/" + id + ".json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(reader, REVIEW_TYPE); // contains the whole reviews list
    }

    public List<FileInfoGridDTO> getAll() {
        List<FileInfoGridDTO> files = new ArrayList<>();
        Type REVIEW_TYPE = new TypeToken<FileInfoGridDTO>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        File[] filesNames = new File("/app").listFiles();
        if (filesNames != null) {
            for (File file : filesNames) {
                if (!file.getName().contains(".json")) continue;
                try {
                    reader = new JsonReader(new FileReader("/app/" + file.getName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                files.add(gson.fromJson(reader, REVIEW_TYPE));
            }
        }
        return files;
    }

    public void saveId(String mode, UUID id) {
        try (PrintWriter out = new PrintWriter("/app/" + mode)) {
            out.append(id.toString() + "\n");
        } catch (IOException e) {
        }
    }

    public List<UUID> readIds(String mode) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/app/" + mode));
            String st;
            List<UUID> uuids = new ArrayList<>();
            while ((st = br.readLine()) != null)
                uuids.add(UUID.fromString(st));
            return uuids;
        } catch (IOException e) {
        }
        return new ArrayList<>();
    }

    public void deleteFile(String mode) {
        File file = new File("/app/" + mode);
        file.delete();
    }
}

