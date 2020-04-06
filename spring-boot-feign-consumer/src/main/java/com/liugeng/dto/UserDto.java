package com.liugeng.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -6987352133256694672L;

    private Long Id;

    private String name;
}
