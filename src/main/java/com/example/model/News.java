package com.example.model;

import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private int aid;
    private String title;
    private String img;
    private String DATE;
    private String content;


}
