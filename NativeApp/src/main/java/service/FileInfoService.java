package service;


import Domain.CreatedFileInfoDTO;
import Domain.FileInfoGridDTO;
import Domain.FullFileInfoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jobs.RequestJobs;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileInfoService {

    private static final String url = "http://localhost:8080/files";
    private static final String USER_AGENT = "Mozilla/5.0";
    public static Boolean offline = false;
    private final FileService fileService;
    private Gson g;

    public FileInfoService() {
        fileService = new FileService();
        g = new Gson();
    }

    public List<FileInfoGridDTO> getAllFiles() {
        if (!offline) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);

            // add request header
            request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = null;
            try {
                response = client.execute(request);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                Type type = new TypeToken<List<FileInfoGridDTO>>() {
                }.getType();

                List<FileInfoGridDTO> resources = g.fromJson(result.toString(), type);
                resources.forEach(file -> {
                    fileService.updateFile(file.getId(), file.getContent());
                    fileService.saveMetadata(file);
                });
                return resources;

            } catch (IOException e) {
                return fileService.getAll();
            } finally {
                ((DefaultHttpClient) client).close();
            }
        } else {
            return fileService.getAll();
        }
    }

    public List<String> getChatUsers() {
        if (!offline) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url + "/chat");

            // add request header
            request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = null;
            try {
                response = client.execute(request);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                Type type = new TypeToken<List<String>>() {
                }.getType();

                System.out.println(result.toString());
                List<String> resources = g.fromJson(result.toString(), type);
                return resources;
            } catch (IOException e) {
                return new ArrayList<>(Arrays.asList(new String[]{"Nie mozna nawiazac polaczenia"}));
            } finally {
                ((DefaultHttpClient) client).close();
            }
        } else {
            return new ArrayList<>(Arrays.asList(new String[]{"Nie mozna nawiazac polaczenia"}));
        }
    }

    public void addFile(CreatedFileInfoDTO createdFileInfoDTO) {
        if (!offline) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setHeader("User-Agent", USER_AGENT);
            post.addHeader("content-type", "application/json");
            try {
                StringEntity params = new StringEntity(g.toJson(createdFileInfoDTO));
                post.setEntity(params);
                HttpResponse response = client.execute(post);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException e) {
                addToCreateQueue(createdFileInfoDTO);
            }
        } else {
            addToCreateQueue(createdFileInfoDTO);
        }
    }

    private void addToCreateQueue(CreatedFileInfoDTO createdFileInfoDTO) {
        FileInfoGridDTO file = new FileInfoGridDTO();
        file.setId(UUID.randomUUID());
        file.setContent(createdFileInfoDTO.getContent());
        file.setDescription(createdFileInfoDTO.getDescription());
        file.setName(createdFileInfoDTO.getName());
        fileService.updateFile(file.getId(), file.getContent());
        fileService.saveMetadata(file);
        fileService.saveId("create", file.getId());
        RequestJobs.filesToCreate.add(file.getId());
    }

    public void addUser(String nickname) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url + "/chat/" + nickname);
        post.setHeader("User-Agent", USER_AGENT);
        post.addHeader("content-type", "application/json");
        try {
            HttpResponse response = client.execute(post);
        } catch (IOException e) {
        }
    }

    public FullFileInfoDTO getFile(UUID uuid) {
        if (!offline) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url + "/" + uuid.toString());

            // add request header
            request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = null;
            try {
                response = client.execute(request);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                return g.fromJson(result.toString(), FullFileInfoDTO.class);
            } catch (IOException e) {
                return fileService.getOne(uuid);
            } finally {
                ((DefaultHttpClient) client).close();
            }
        } else {
            return fileService.getOne(uuid);
        }
    }

    public FullFileInfoDTO editFile(CreatedFileInfoDTO createdFileInfoDTO, UUID uuid) {
        if (!offline) {
            HttpClient client = new DefaultHttpClient();
            HttpPut put = new HttpPut(url + "/" + uuid.toString());
            put.setHeader("User-Agent", USER_AGENT);
            put.addHeader("content-type", "application/json");
            try {
                StringEntity params = new StringEntity(g.toJson(createdFileInfoDTO));
                put.setEntity(params);
                HttpResponse response = client.execute(put);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                return g.fromJson(result.toString(), FullFileInfoDTO.class);
            } catch (IOException e) {
                return addFileToEditQueue(createdFileInfoDTO, uuid);
            }
        } else {
            return addFileToEditQueue(createdFileInfoDTO, uuid);
        }
    }

    private FullFileInfoDTO addFileToEditQueue(CreatedFileInfoDTO createdFileInfoDTO, UUID uuid) {
        FileInfoGridDTO file = new FileInfoGridDTO();
        file.setId(uuid);
        file.setContent(createdFileInfoDTO.getContent());
        file.setDescription(createdFileInfoDTO.getDescription());
        file.setName(createdFileInfoDTO.getName());
        file.setVersion(createdFileInfoDTO.getVersion());
        fileService.updateFile(file.getId(), file.getContent());
        fileService.saveMetadata(file);
        if (!RequestJobs.filesToCreate.contains(uuid)) {
            RequestJobs.filesToUpdate.add(uuid);
            fileService.saveId("update", uuid);
        }
        return fileService.getOne(uuid);
    }

}
