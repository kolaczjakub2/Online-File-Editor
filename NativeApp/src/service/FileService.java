package service;


import Domain.CreatedFileInfoDTO;
import Domain.FileInfoGridDTO;
import Domain.FullFileInfoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.util.List;
import java.util.UUID;

public class FileService {

    private static final String url = "http://localhost:8080/files";
    private static final String USER_AGENT = "Mozilla/5.0";
    private Gson g;
    public FileService() {
        g=new Gson();
    }

    public List<FileInfoGridDTO> getAllFiles() {

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

            return g.fromJson(result.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ((DefaultHttpClient) client).close();
        }
        return null;
    }

    public void addFile(CreatedFileInfoDTO createdFileInfoDTO) {
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

            System.out.println(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FullFileInfoDTO getFile(UUID uuid) {
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
            e.printStackTrace();
        } finally {
            ((DefaultHttpClient) client).close();
        }
        return null;
    }

    public FullFileInfoDTO editFile(CreatedFileInfoDTO createdFileInfoDTO, UUID uuid) {
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
            System.out.println(result.toString());
            return g.fromJson(result.toString(), FullFileInfoDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
