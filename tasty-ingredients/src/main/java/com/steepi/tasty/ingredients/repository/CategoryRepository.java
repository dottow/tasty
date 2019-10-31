package com.steepi.tasty.ingredients.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.steepi.tasty.ingredients.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	Optional<Category> findByNameIgnoreCase(String name);
}
