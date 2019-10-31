package com.steepi.tasty.ingredients.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.steepi.tasty.ingredients.dto.IngredientDto;
import com.steepi.tasty.ingredients.model.Category;
import com.steepi.tasty.ingredients.model.Ingredient;
import com.steepi.tasty.ingredients.repository.IngredientRepository;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceTests {
	private final static Category BAKERY = Category.builder().name("Bakery").build();
	private final static Category DAIRY = Category.builder().name("Dairy").build();
	private final static Category MEAT = Category.builder().name("Meat").build();
	private final static Category VEGETABLES = Category.builder().name("Vegetables").build();
	private IngredientRepository ingredientRepository;
	private IngredientService ingredientService;
	
	@Before
	public void setUp() {
		ingredientRepository = Mockito.mock(IngredientRepository.class);
		ingredientService = new IngredientService(ingredientRepository);
	}
	
	@Test
	public void whenIngredientExists_thenReturnAsExpected() throws Exception {
		Mockito.when(ingredientRepository.findByNameContainingIgnoreCase("Lemongrass"))
			.thenReturn(Optional.of(Ingredient.builder().name("Lemongrass").category(VEGETABLES).build()));
		
		IngredientDto ingredientDto = IngredientDto.builder().name("Lemongrass").build();
		ingredientDto = ingredientService.read(ingredientDto);
		assertNotNull(ingredientDto);
		assertEquals(VEGETABLES, ingredientDto.getCategory());
		assertEquals("Lemongrass", ingredientDto.getName());
	}

}
