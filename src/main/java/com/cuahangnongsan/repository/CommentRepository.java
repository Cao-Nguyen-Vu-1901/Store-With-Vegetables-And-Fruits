package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProduct(Product product);
    List<Comment> findAllByCreatedDateBefore(LocalDateTime time);
    List<Comment> findAllByUsernameLike(String username);
    List<Comment> findAllByContentLike(String content);
}
