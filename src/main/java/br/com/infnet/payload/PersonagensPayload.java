package br.com.infnet.payload;

import br.com.infnet.model.Personagem;
import lombok.Data;

import java.util.List;

@Data
public class PersonagensPayload {
    private Info info;
    private List<Personagem> results;
}
