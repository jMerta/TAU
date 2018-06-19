package io.jmerta.tau.domain.taskManagment.entity;

import lombok.Data;

@Data
public class Category {

    private long id;
    private String name;
    private long accountId;
    private String content;
    private String comment;

}
