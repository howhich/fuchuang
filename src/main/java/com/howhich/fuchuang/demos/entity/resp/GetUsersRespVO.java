package com.howhich.fuchuang.demos.entity.resp;

import com.howhich.fuchuang.demos.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class GetUsersRespVO {
    private List<User> list;
    private Long total;
}
