package com.geosieben.gsbworkday.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class AssesmentsQuestionsDto {
    private int id;
    private String text;
    private List<String> options;
    private int correctIndex;
    public AssesmentsQuestionsDto(int id, String text, List<String> options, int correctIndex) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }

}
