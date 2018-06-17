package io.jmerta.tau.domain.taskManagment.entity;

import lombok.Data;

import java.util.List;

@Data
public class Task {

    private long id;
    private boolean isDone;
    private String name;
    private String content;
    private List<String> comments;
}
