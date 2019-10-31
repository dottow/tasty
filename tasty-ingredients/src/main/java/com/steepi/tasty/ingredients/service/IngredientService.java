package com.steepi.tasty.ingredients.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steepi.tasty.ingredients.dto.CategoryDto;
import com.steepi.tasty.ingredients.dto.IngredientDto;
import com.steepi.tasty.ingredients.exception.IngredientException;
import com.steepi.tasty.ingredients.model.Category;
import com.steepi.tasty.ingredients.model.Ingredient;
import com.steepi.tasty.ingredients.repository.IngredientRepository;

@Service
public class IngredientService {
	private IngredientRepository ingredientRepository;
	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	public IngredientService(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}
	
	public List<IngredientDto> getIngredients(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		List<Ingredient> ingredients = ingredientRepository.findByCategory(category);
		Collections.sort(ingredients);
		return ingredients.stream()
				.map(ingredient -> modelMapper.map(ingredients, IngredientDto.class))
				.collect(Collectors.toList());
	}
	
	public IngredientDto create(IngredientDto ingredientDto) throws IngredientException {
		Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
		Optional<Ingredient> existing = ingredientRepository.findByNameContainingIgnoreCase(ingredient.getName());
		if (existing.isPresent() && ingredient.equals(existing.get())) {
			throw new IngredientException("Ingredient '"+ingredient.getName()+"' already exists.");
		}
		ingredient = ingredientRepository.save(ingredient);
		return modelMapper.map(ingredient, IngredientDto.class);
	}
	
	public IngredientDto read(IngredientDto ingredientDto) throws IngredientException {
		Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
		Optional<Ingredient> existing = ingredientRepository.findByNameContainingIgnoreCase(ingredient.getName());
		if (!existing.isPresent()) {
			throw new IngredientException("Ingredient '"+ingredient.getName()+"' not found.");
		}
		return modelMapper.map(existing.get(), IngredientDto.class);
	}
	
	public IngredientDto update(IngredientDto ingredientDto) throws IngredientException {
		Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
		Optional<Ingredient> existing = ingredientRepository.findByNameContainingIgnoreCase(ingredient.getName());
		if (!existing.isPresent()) {
			throw new IngredientException("Ingredient '"+ingredient.getName()+"' not found.");
		}
		Ingredient updated = existing.get();
		updated.setName(ingredient.getName());
		updated.setCategory(ingredient.getCategory());
		ingredient = ingredientRepository.save(ingredient);
		return modelMapper.map(ingredient, IngredientDto.class);		
	}
	
	public void delete(IngredientDto ingredientDto) throws IngredientException {
		Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
		Optional<Ingredient> existing = ingredientRepository.findByNameContainingIgnoreCase(ingredient.getName());
		if (!existing.isPresent()) {
			return;
		}
		ingredientRepository.delete(existing.get());
	}
}
