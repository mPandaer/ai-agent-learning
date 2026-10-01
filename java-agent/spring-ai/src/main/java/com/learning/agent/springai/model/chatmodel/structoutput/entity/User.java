package com.learning.agent.springai.model.chatmodel.structoutput.entity;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
public class User {

    @JsonPropertyDescription("名字")
    private String name;

    @JsonPropertyDescription("年龄，大于18岁")
    private Integer age;

    @JsonPropertyDescription("职位，枚举值：员工 小组长 领导")
    private String position;

    @JsonPropertyDescription("所管辖的人员，length <= 3 && length > 0")
    private List<User> users;
}
