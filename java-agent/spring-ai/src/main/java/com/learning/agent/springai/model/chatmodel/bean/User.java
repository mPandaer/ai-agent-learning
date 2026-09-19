package com.learning.agent.springai.model.chatmodel.bean;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
public class User {

    @JsonPropertyDescription("登录名")
    private String loginId;

    @JsonPropertyDescription("密码,MD5值")
    private String password;

    @JsonPropertyDescription("姓名")
    private String name;

    @JsonPropertyDescription("所属单位")
    private String corp;

    @JsonPropertyDescription("可以管理的人员")
    private List<User> users;
}
