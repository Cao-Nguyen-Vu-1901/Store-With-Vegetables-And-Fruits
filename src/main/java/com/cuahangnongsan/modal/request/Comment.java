package com.cuahangnongsan.modal.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {


    private String content;

    private Comment parent;

//    private List<Comment> replies = new ArrayList<>();


}

