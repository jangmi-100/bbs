package com.springbootstudy.bbs.domain;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
private int no;
private String title;
private String writer;
private String content;
private Timestamp regDate;
private int readCount;
private String pass;
private String file1;
}
