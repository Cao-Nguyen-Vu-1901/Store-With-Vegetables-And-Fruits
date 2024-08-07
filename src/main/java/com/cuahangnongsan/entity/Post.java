package com.cuahangnongsan.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;

    @Column(columnDefinition = "TEXT")
    String content;

    LocalDateTime createdTime;

    LocalDateTime modifiedTime;

    String image;

    String shortDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
