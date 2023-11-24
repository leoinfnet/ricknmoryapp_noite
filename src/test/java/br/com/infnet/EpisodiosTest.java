package br.com.infnet;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Episodio;
import br.com.infnet.util.EpisodioUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class EpisodiosTest {
    @Test
    @DisplayName("Deve retornar um episodio pelo ID")
    public void testaEpisodioPeloId(){
        EpisodioUtil episodioUtil = new EpisodioUtil();
        Episodio episodio = episodioUtil.getById(1);
        assertEquals("Pilot", episodio.getName());
        assertEquals(1, episodio.getId());
    }
    @Test
    @DisplayName("Deve retornar uma exception para um episodio Inexistente")
    public void testaEpisodioInexistente(){
        EpisodioUtil episodioUtil = new EpisodioUtil();
        assertThrows(ResourceNotFoundException.class, () ->{
           episodioUtil.getById(-4);
        });

    }


}
