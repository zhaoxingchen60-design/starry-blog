package com.atguigu.blo.repository;

import com.atguigu.blo.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCategoryOrderByCreatedAtDesc(String category);

    List<Article> findAllByOrderByCreatedAtDesc();

    @Query("SELECT DISTINCT a.category FROM Article a")
    List<String> findDistinctCategories();

    List<Article> findByTitleContainingOrContentContainingOrTagsContainingOrderByCreatedAtDesc(
            String title, String content, String tags);
}
