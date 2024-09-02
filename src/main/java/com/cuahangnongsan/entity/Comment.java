package com.cuahangnongsan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @Column(columnDefinition = "TEXT")
     String content;

     String username;

     String imageUser;

     LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @JsonBackReference(value = "comment_json")
    Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "comment_json")
     List<Comment> replies = new ArrayList<>();


    @JsonBackReference(value = "product_comment_json")
    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    Product product;

}



