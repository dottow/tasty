package com.steepi.tasty.ingredients.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.steepi.tasty.ingredients.model.Category;
import com.steepi.tasty.ingredients.model.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
	Optional<Ingredient> findByNameContainingIgnoreCase(String name);
	List<Ingredient> findByCategory(Category category);
}
