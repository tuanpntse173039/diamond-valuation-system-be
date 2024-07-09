package com.letitbee.diamondvaluationsystem.repository;

import com.letitbee.diamondvaluationsystem.entity.Post;
import com.letitbee.diamondvaluationsystem.enums.BlogType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.title = :title and p.id != :id")
    List<Post> existsByTitleUpdate(String title, long id);

    boolean existsByTitle(String title);

    @Query("SELECT p " +
            "FROM Post p " +
            "WHERE p.status = :status or :status IS NULL ")
    Page<Post> findAllByStatus(Pageable pageable, BlogType status);

    Post findByTitle(String title);
}
