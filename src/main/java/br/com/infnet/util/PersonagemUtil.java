package br.com.infnet.util;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Personagem;
import br.com.infnet.payload.PersonagensPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PersonagemUtil {
    public Personagem getByURL(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI(url))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //ObjectMapper objectMapper = new ObjectMapper();
            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            Personagem personagem = mapper.readValue(response.body(), Personagem.class);

            return personagem;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }



    }
    public List<Personagem> getByURL(List<String> urls) {
        List<Personagem> personagens = urls.stream()
                .map(url -> {
                    return  getByURL(url);
                }).toList();
        return personagens;
    }

    public Personagem getById(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI("https://rickandmortyapi.com/api/character/" + id))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 404){
                throw new ResourceNotFoundException(response.body());
            }
            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            Personagem personagem = mapper.readValue(response.body(), Personagem.class);
            return personagem;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAvatar(Personagem personagem){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(personagem.getImage()))
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            Path path = Path.of("avatares/" + personagem.getName() + ".png");
            Files.write(path, response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public PersonagensPayload getByName(String name) {
            return getByName(name, 1);
    }
    public PersonagensPayload getByName(String name, int page) {
        String url = "https://rickandmortyapi.com/api/character?name=" + name + "&page=" + page;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .uri(new URI(url))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            PersonagensPayload personagensPayload = mapper.readValue(response.body(), PersonagensPayload.class);
            return personagensPayload;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
