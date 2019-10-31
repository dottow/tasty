package com.steepi.tasty.ingredients.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.steepi.tasty.ingredients.model.Category;
import com.steepi.tasty.ingredients.model.Ingredient;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IngredientRepositoryTests {
	@Autowired
    private TestEntityManager testEntityManager;
	@Autowired
	private IngredientRepository ingredientRepository;
	private Category bakery, beverages, meat, seafood;
	
	@Before
	public void setUp() {
		bakery = Category.builder().name("Bakery").build();
		beverages = Category.builder().name("Beverages").build();
		meat = Category.builder().name("Meat").build();
		seafood = Category.builder().name("Seafood").build();
		bakery = testEntityManager.persist(bakery);
		beverages = testEntityManager.persist(beverages);
		meat = testEntityManager.persist(meat);
		seafood = testEntityManager.persist(seafood);
		testEntityManager.persist(Ingredient.builder().category(bakery).name("Baguette").build());
		testEntityManager.persist(Ingredient.builder().category(bakery).name("Focaccia").build());
		testEntityManager.persist(Ingredient.builder().category(beverages).name("Iced Tea").build());
		testEntityManager.persist(Ingredient.builder().category(beverages).name("Ginger Ale").build());
		testEntityManager.persist(Ingredient.builder().category(meat).name("Ground Beef").build());
		testEntityManager.persist(Ingredient.builder().category(meat).name("Pork Shoulder").build());
		testEntityManager.persist(Ingredient.builder().category(meat).name("Veal Chops").build());
		testEntityManager.persist(Ingredient.builder().category(seafood).name("Oysters").build());
		testEntityManager.persist(Ingredient.builder().category(seafood).name("Halibut").build());
		testEntityManager.persist(Ingredient.builder().category(seafood).name("Trout").build());
		testEntityManager.persist(Ingredient.builder().category(seafood).name("Scallops").build());
	}
	
	@Test
	public void testCountIngredients() {
		assertEquals(11, ingredientRepository.count());
	}
	
	@Test
	public void testFindIngredientByName() {
		assertEquals(false, ingredientRepository.findByNameContainingIgnoreCase("Cumin").isPresent());
		assertEquals(true, ingredientRepository.findByNameContainingIgnoreCase("Baguette").isPresent());
		assertEquals(true, ingredientRepository.findByNameContainingIgnoreCase("OYSTER").isPresent());
		assertEquals(true, ingredientRepository.findByNameContainingIgnoreCase("Beef").isPresent());
		assertEquals(true, ingredientRepository.findByNameContainingIgnoreCase("veal").isPresent());
	}

	@Test
	public void testFindIngredientByCategory() {
		List<Ingredient> ingredients = ingredientRepository.findByCategory(bakery);
		assertNotNull(ingredients);
		assertEquals(2, ingredients.size());
		Set<String> ingredientNames = new HashSet<String>();
		ingredientNames.add("Baguette");
		ingredientNames.add("Focaccia");
		for (Ingredient ingredient : ingredients) {
			assertTrue(ingredientNames.contains(ingredient.getName()));
		}
		
		ingredients = ingredientRepository.findByCategory(seafood);
		assertNotNull(ingredients);
		assertEquals(4, ingredients.size());
		ingredientNames = new HashSet<String>();
		ingredientNames.add("Halibut");
		ingredientNames.add("Oysters");
		ingredientNames.add("Trout");
		ingredientNames.add("Scallops");
		for (Ingredient ingredient : ingredients) {
			assertTrue(ingredientNames.contains(ingredient.getName()));
		}
		
	}
	
	@Test
	public void testSaveIngredient() {
		List<Ingredient> ingredients = ingredientRepository.findByCategory(meat);
		assertNotNull(ingredients);
		assertEquals(3, ingredients.size());
		ingredientRepository.save(Ingredient.builder().category(meat).name("Bratwurst").build());
		ingredients = ingredientRepository.findByCategory(meat);
		assertNotNull(ingredients);
		assertEquals(4, ingredients.size());
		Set<String> ingredientNames = new HashSet<String>();
		ingredientNames.add("Bratwurst");
		ingredientNames.add("Ground Beef");
		ingredientNames.add("Veal Chops");
		ingredientNames.add("Pork Shoulder");
		for (Ingredient ingredient : ingredients) {
			assertTrue(ingredientNames.contains(ingredient.getName()));
		}		
	}
	
	@Test
	public void testRemoveIngredient() {
		List<Ingredient> ingredients = ingredientRepository.findByCategory(seafood);
		assertNotNull(ingredients);
		assertEquals(4, ingredients.size());
		Set<String> ingredientNames = new HashSet<String>();
		ingredientNames.add("Halibut");
		ingredientNames.add("Oysters");
		ingredientNames.add("Trout");
		ingredientNames.add("Scallops");
		for (Ingredient ingredient : ingredients) {
			assertTrue(ingredientNames.contains(ingredient.getName()));
		}
		ingredientRepository.delete(ingredientRepository.findByNameContainingIgnoreCase("Trout").get());
		ingredients = ingredientRepository.findByCategory(seafood);
		assertNotNull(ingredients);
		assertEquals(3, ingredients.size());
		ingredientNames.remove("Trout");
		for (Ingredient ingredient : ingredients) {
			assertTrue(ingredientNames.contains(ingredient.getName()));
		}		
	}
}
