package com.inhatc.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BoardVO {

    private int bno;
    private String title;
    private String content;
    private String writer;
    private Date regdate;
    private int viewcnt;

    public BoardVO() {
        super();
    }

}
