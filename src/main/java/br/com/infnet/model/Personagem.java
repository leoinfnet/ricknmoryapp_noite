package br.com.infnet.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Personagem {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String image;
    private List<String> episode;
    private String url;
    private LocalDateTime created;
    private Location location;
    private Location origin;
}
