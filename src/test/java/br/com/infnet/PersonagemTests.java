package br.com.infnet;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Personagem;
import br.com.infnet.payload.PersonagensPayload;
import br.com.infnet.util.PersonagemUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonagemTests {
    @Test
    @DisplayName("Deve retornar o personagem pela URL")
    public void getByURL(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        Personagem personagem = personagemUtil.getByURL("https://rickandmortyapi.com/api/character/300");
        assertEquals("Roger", personagem.getName());
    }
    @Test
    @DisplayName("Deve retornar uma lista de Personagens pela Lista de URLs")
    public void getByList(){
        List<String> listaDePersonagens = getListaDePersonagens();
        PersonagemUtil personagemUtil = new PersonagemUtil();
        List<Personagem> persongensDoEpisodio = personagemUtil.getByURL(listaDePersonagens);
        Personagem personagem = persongensDoEpisodio.get(0);
        assertEquals("Rick Sanchez" , personagem.getName());
        assertEquals(listaDePersonagens.size(),persongensDoEpisodio.size());
    }
    @Test
    @DisplayName("Deve retornar um personagem Pelo ID")
    public void getByID(){
        List<String> listaDePersonagens = getListaDePersonagens();
        PersonagemUtil personagemUtil = new PersonagemUtil();
        Personagem personagem = personagemUtil.getById(1);

        assertEquals("Rick Sanchez" , personagem.getName());

    }
    @Test
    @DisplayName("Deve retornar uma exception para um personagem inexistente")
    public void testaPersonagemInexistente(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        assertThrows(ResourceNotFoundException.class, () ->{
            personagemUtil.getById(-1);
        });

    }
    @Test
    @DisplayName("Deve salvar o avatar do personagem")
    public void testaSaveAvatar(){

        PersonagemUtil personagemUtil = new PersonagemUtil();
        Personagem personagem = personagemUtil.getById(556);
        Path avatarPath = Path.of("avatares/" + personagem.getName() + ".png");
        try {
            Files.deleteIfExists(avatarPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        personagemUtil.getAvatar(personagem);
        boolean exists = Files.exists(avatarPath);
        assertTrue(exists);

    }
    @Test
    @DisplayName("Deve buscar um personagem pelo nome")
    public void buscaPersonagemPeloNome(){
        PersonagemUtil personagemUtil = new PersonagemUtil();
        PersonagensPayload personagens = personagemUtil.getByName("Rick");
        int count = personagens.getInfo().getCount();
        assertEquals(107, count );
        Personagem personagem = personagens.getResults().get(0);
        assertEquals("Rick Sanchez", personagem.getName());
        personagens = personagemUtil.getByName("Rick",2);
        personagem = personagens.getResults().get(0);
        assertEquals("Mechanical Rick", personagem.getName());
        String nextPage = personagens.getInfo().getNext().split("=")[1].substring(0,1);
        assertEquals("3",nextPage);



    }

    private List<String> getListaDePersonagens() {
          return List.of("https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2",
                "https://rickandmortyapi.com/api/character/35",
                "https://rickandmortyapi.com/api/character/38",
                "https://rickandmortyapi.com/api/character/62",
                "https://rickandmortyapi.com/api/character/92",
                "https://rickandmortyapi.com/api/character/127",
                "https://rickandmortyapi.com/api/character/144",
                "https://rickandmortyapi.com/api/character/158",
                "https://rickandmortyapi.com/api/character/175",
                "https://rickandmortyapi.com/api/character/179",
                "https://rickandmortyapi.com/api/character/181",
                "https://rickandmortyapi.com/api/character/239",
                "https://rickandmortyapi.com/api/character/249",
                "https://rickandmortyapi.com/api/character/271",
                "https://rickandmortyapi.com/api/character/338",
                "https://rickandmortyapi.com/api/character/394",
                "https://rickandmortyapi.com/api/character/395",
                "https://rickandmortyapi.com/api/character/435");
    }
}
