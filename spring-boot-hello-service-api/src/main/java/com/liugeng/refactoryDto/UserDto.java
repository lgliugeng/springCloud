package com.liugeng.refactoryDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 4274011898846127791L;

    private Long Id;

    private String name;
}
