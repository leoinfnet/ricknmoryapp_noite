package br.com.infnet.model;

import lombok.Data;

import java.util.List;

@Data
public class Episodio {
    private int id;
    private String name;
    private String airDate;
    private String episode;
    private List<String> characters;
    private String url;
    private String created;

    public String getAir_date() {
        return airDate;
    }

    public void setAir_date(String airDate) {
        this.airDate = airDate;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }
}
