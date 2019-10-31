package com.steepi.tasty.ingredients.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.steepi.tasty.ingredients.dto.CategoryDto;
import com.steepi.tasty.ingredients.exception.CategoryException;
import com.steepi.tasty.ingredients.model.Category;
import com.steepi.tasty.ingredients.repository.CategoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTests {
	private CategoryRepository categoryRepository;
	private CategoryService categoryService;
	
	@Before
	public void setUp() {
		categoryRepository = Mockito.mock(CategoryRepository.class);
		categoryService = new CategoryService(categoryRepository);
	}
	
	@Test
	public void whenCategoryExists_thenReturnAsExpected() throws Exception {
		Mockito.when(categoryRepository.findByNameIgnoreCase("Seafood")).thenReturn(Optional.of(Category.builder().name("Seafood").build()));
		
		CategoryDto categoryDto = new CategoryDto("Seafood");
		categoryDto = categoryService.read(categoryDto);
		assertNotNull(categoryDto);
		assertEquals("Seafood", categoryDto.getName());
	}
	
	@Test
	public void whenCategoryDoesNotExist_thenThrowException() throws Exception {
		Mockito.when(categoryRepository.findByNameIgnoreCase("Meat")).thenReturn(Optional.empty());
		
		CategoryDto categoryDto = new CategoryDto("Meat");
		try {
			categoryDto = categoryService.read(categoryDto);
			fail("Should throw exception");
		}
		catch (CategoryException ce) {
			assertEquals("Category 'Meat' not found.", ce.getMessage());
		}
	}
	
	@Test
	public void whenCategorySearchIsCalled_thenReturnExpectedData() throws Exception {
		Set<Category> categories = new HashSet<Category>();
		categories.add(Category.builder().name("Meat").build());
		categories.add(Category.builder().name("Spices and Seasonings").build());
		categories.add(Category.builder().name("Dairy").build());
		categories.add(Category.builder().name("Fruits").build());
		categories.add(Category.builder().name("Deli").build());
		categories.add(Category.builder().name("Seafood").build());
		categories.add(Category.builder().name("Bakery").build());
		Mockito.when(categoryRepository.findAll()).thenReturn(categories);

		List<CategoryDto> categoryDtos = categoryService.getAllCategories();
		assertNotNull(categoryDtos);
		assertEquals(7, categoryDtos.size());
		assertEquals("Bakery", categoryDtos.get(0).getName());
		assertEquals("Dairy", categoryDtos.get(1).getName());
		assertEquals("Deli", categoryDtos.get(2).getName());
		assertEquals("Fruits", categoryDtos.get(3).getName());
		assertEquals("Meat", categoryDtos.get(4).getName());
		assertEquals("Seafood", categoryDtos.get(5).getName());
		assertEquals("Spices and Seasonings", categoryDtos.get(6).getName());
	}

	@Test
	public void whenCategoryDoesNotExist_thenCreationReturnsAsExpected() throws Exception {
		Mockito.when(categoryRepository.save(any(Category.class))).then(returnsFirstArg());
		
		CategoryDto categoryDto = CategoryDto.builder().name("Seafood").build();
		categoryDto = categoryService.create(categoryDto);
		assertNotNull(categoryDto);
		assertEquals("Seafood", categoryDto.getName());
	}

	@Test
	public void whenCategoryExists_thenCreationThrowsException() throws Exception {
		Mockito.when(categoryRepository.findByNameIgnoreCase("Seafood")).thenReturn(Optional.of(Category.builder().name("Seafood").build()));
		
		CategoryDto categoryDto = CategoryDto.builder().name("Seafood").build();
		try {
			categoryService.create(categoryDto);
			fail("Should throw exception");
		}
		catch (CategoryException ce) {
			assertEquals("Category 'Seafood' already exists.", ce.getMessage());
		}
	}
	
	@Test
	public void whenCategoryExists_thenUpdateReturnsAsExpected() throws Exception {
		Mockito.when(categoryRepository.findByNameIgnoreCase("Seafood")).thenReturn(Optional.of(Category.builder().name("SeaFOOD").build()));
		Mockito.when(categoryRepository.save(any(Category.class))).then(returnsFirstArg());

		CategoryDto categoryDto = CategoryDto.builder().name("Seafood").build();
		categoryDto = categoryService.read(categoryDto);
		assertNotNull(categoryDto);
		assertEquals("SeaFOOD", categoryDto.getName());
		categoryDto.setName("Seafood");;
		categoryDto = categoryService.update(categoryDto);
		assertNotNull(categoryDto);
		assertEquals("Seafood", categoryDto.getName());
	}
	
	@Test
	public void whenCategoryDoesNotExist_thenUpdateThrowsException() throws Exception {
		CategoryDto categoryDto = CategoryDto.builder().name("Seafood").build();
		try {
			categoryService.update(categoryDto);
			fail("Should throw exception");
		}
		catch (CategoryException ce) {
			assertEquals("Category 'Seafood' not found.", ce.getMessage());
		}

	}

	@Test
	public void whenCategoryDoesNotExist_thenDeleteReturnsAsExpected() throws Exception {
		CategoryDto categoryDto = CategoryDto.builder().name("Seafood").build();
		categoryService.delete(categoryDto);
	}

	@Test
	public void whenCategoryExists_thenDeleteReturnsAsExpected() throws Exception {
		Mockito.when(categoryRepository.findByNameIgnoreCase("Seafood")).thenReturn(Optional.of(Category.builder().name("SeaFOOD").build()));
		CategoryDto categoryDto = CategoryDto.builder().name("Seafood").build();
		categoryService.delete(categoryDto);		
	}
}
