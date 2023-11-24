package br.com.infnet.payload;

import lombok.Data;

@Data
public class Info {
    private int count;
    private int pages;
    private String prev;
    private String next;
}
