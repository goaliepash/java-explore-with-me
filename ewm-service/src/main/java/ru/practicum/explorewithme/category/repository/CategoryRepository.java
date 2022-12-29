package ru.practicum.explorewithme.category.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.category.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category AS c")
    List<Category> get(Pageable pageable);

    @Query("SELECT c FROM Category AS c WHERE c.name = :name")
    List<Category> findAllByName(String name);
}