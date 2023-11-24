package br.com.infnet.util;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Episodio;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EpisodioUtil {
    public Episodio getById(int id) {
        String uri = "https://rickandmortyapi.com/api/episode/" + id;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(uri))
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 404){
                throw new ResourceNotFoundException(response.body());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Episodio episodio = objectMapper.readValue(response.body(), Episodio.class);

            //Reflection
            return episodio;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
