package com.article.hub.DAO;

import com.article.hub.Entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Boolean existsByNameIgnoreCase(String name);

    @Modifying
    @Transactional
    Integer updateCategory(@Param("name") String name, @Param("id") Integer id);
}
