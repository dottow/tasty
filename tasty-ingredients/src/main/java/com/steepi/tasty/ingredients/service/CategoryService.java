package com.steepi.tasty.ingredients.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steepi.tasty.ingredients.dto.CategoryDto;
import com.steepi.tasty.ingredients.exception.CategoryException;
import com.steepi.tasty.ingredients.model.Category;
import com.steepi.tasty.ingredients.repository.CategoryRepository;

@Service
public class CategoryService {
	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	public CategoryService(final CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		for (Category category : categoryRepository.findAll()) {
			categories.add(category);
		}
		Collections.sort(categories);
		return categories.stream()
				.map(category -> modelMapper.map(category, CategoryDto.class))
				.collect(Collectors.toList());
	}
	
	public CategoryDto create(CategoryDto categoryDto) throws CategoryException {
		Category category = modelMapper.map(categoryDto, Category.class);
		Optional<Category> existing = categoryRepository.findByNameIgnoreCase(category.getName());
		if (existing.isPresent() && category.equals(existing.get())) {
			throw new CategoryException("Category '"+category.getName()+"' already exists.");
		}
		category = categoryRepository.save(category);
		return modelMapper.map(category, CategoryDto.class);
	}

	public CategoryDto read(CategoryDto categoryDto) throws CategoryException {
		Category category = modelMapper.map(categoryDto, Category.class);
		Optional<Category> existing = categoryRepository.findByNameIgnoreCase(category.getName());
		if (!existing.isPresent()) {
			throw new CategoryException("Category '"+category.getName()+"' not found.");
		}
		return modelMapper.map(existing.get(), CategoryDto.class);
		
	}

	public CategoryDto update(CategoryDto categoryDto) throws CategoryException {
		Category category = modelMapper.map(categoryDto, Category.class);
		Optional<Category> existing = categoryRepository.findByNameIgnoreCase(category.getName());
		if (!existing.isPresent()) {
			throw new CategoryException("Category '"+category.getName()+"' not found.");
		}
		Category updated = existing.get();
		updated.setName(category.getName());
		updated = categoryRepository.save(updated);
		return modelMapper.map(updated, CategoryDto.class);
	}

	public void delete(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Optional<Category> existing = categoryRepository.findByNameIgnoreCase(category.getName());
		if (!existing.isPresent()) {
			return;
		}
		categoryRepository.delete(existing.get());
	}

}
