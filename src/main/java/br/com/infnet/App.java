package br.com.infnet;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Episodio;
import br.com.infnet.model.Personagem;
import br.com.infnet.payload.PersonagensPayload;
import br.com.infnet.util.EpisodioUtil;
import br.com.infnet.util.PersonagemUtil;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Seja Vem vindo a API de RICK N MORTY" );
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a opcao desejada");
        System.out.println("1 - Buscar episodio pelo ID");
        System.out.println("2 - Buscar Personagem pelo ID");
        System.out.println("3 - Buscar Personagem Pelo Nome");
        System.out.println("4 - Buscar Personagens");
        System.out.println("5 - Buscar Avatar pelo ID do Personagem");
        int opcaoUsuario = scanner.nextInt();
        EpisodioUtil episodioUtil = new EpisodioUtil();

        PersonagemUtil personagemUtil = new PersonagemUtil();
        switch (opcaoUsuario){
            case 1: {
                System.out.println("Digite o ID do episodio");
                int episodioId = scanner.nextInt();
                try{
                    Episodio episodio = episodioUtil.getById(episodioId);
                    System.out.println("Nome: " +  episodio.getName());
                    System.out.println("Data Exibicao: " + episodio.getAirDate());
                    System.out.println("Buscando personagens Aguarde");
                    List<Personagem> personagens = personagemUtil.getByURL(episodio.getCharacters());
                    System.out.println("Personagens: ");
                    personagens.forEach(personagem -> {
                        System.out.println("================");
                        System.out.println("ID: " + personagem.getId());
                        System.out.println("Nome: " + personagem.getName());
                        System.out.println("Status: " + personagem.getStatus());
                    });

                }catch (ResourceNotFoundException ex){
                    System.out.println(ex.getMessage());
                }
                break;
            }
            case 2: {
                System.out.println("Digite o ID do personagem");
                int personagemId = scanner.nextInt();

                try {
                    Personagem personagem = personagemUtil.getById(personagemId);
                    System.out.println("Nome: " + personagem.getName());
                    System.out.println("Status: " + personagem.getStatus());
                    System.out.println("Gender: " + personagem.getGender());

                } catch (ResourceNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case 3: {
                System.out.println("Digite o nome do personagem");
                String personagemName = scanner.next();
                PersonagensPayload personagem = personagemUtil.getByName(personagemName);
                int pageCount = personagem.getInfo().getPages();
                int pageAtual = 1;
                personagem.getResults().forEach(personagemRetornado -> {

                    System.out.println("ID: " + personagemRetornado.getId());
                    System.out.println("Nome: " + personagemRetornado.getName());
                    System.out.println("Status: " + personagemRetornado.getStatus());
                    System.out.println("================");
                });

                System.out.println("Q- Quit | P-> next");
                String escolha = scanner.next();
                if(escolha.equalsIgnoreCase("P")){

                }
                personagem = personagemUtil.getByName(personagemName,2);
                personagem.getResults().forEach(personagemRetornado -> {

                    System.out.println("ID: " + personagemRetornado.getId());
                    System.out.println("Nome: " + personagemRetornado.getName());
                    System.out.println("Status: " + personagemRetornado.getStatus());
                    System.out.println("================");
                });
                break;
            }

            case 5:
                System.out.println("Digite o ID do personagem");
                int personagemId = scanner.nextInt();
                try {
                    Personagem personagem = personagemUtil.getById(personagemId);
                    personagemUtil.getAvatar(personagem);
                    System.out.println("Imagem baixada");
                } catch (ResourceNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                break;



        }

    }
}
