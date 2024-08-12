package org.muyangj.bookhaven.repositories;

import org.muyangj.bookhaven.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Find category by name
    Optional<Category> findByName(String name);

    // Delete category by name
    void deleteByName(String name);

    // Find all categories (optional method if custom sorting is needed)
    List<Category> findAllByOrderByNameAsc();
}
