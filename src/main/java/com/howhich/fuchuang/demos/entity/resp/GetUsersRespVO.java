package com.howhich.fuchuang.demos.entity.resp;

import lombok.Data;

import java.util.List;

@Data
public class GetUsersRespVO {
    private List<User> list;
    private Long total;
}
