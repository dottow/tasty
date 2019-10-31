package com.steepi.tasty.ingredients.repository;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.steepi.tasty.ingredients.model.Category;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTests {
	@Autowired
    private TestEntityManager testEntityManager;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Before
	public void setUp() {
		testEntityManager.persist(Category.builder().name("Bakery").build());
		testEntityManager.persist(Category.builder().name("Baking Goods").build());
		testEntityManager.persist(Category.builder().name("Beverages").build());
		testEntityManager.persist(Category.builder().name("Dairy").build());
		testEntityManager.persist(Category.builder().name("Fruits").build());
		testEntityManager.persist(Category.builder().name("Meat").build());
		testEntityManager.persist(Category.builder().name("Seafood").build());
		testEntityManager.persist(Category.builder().name("Vegetables").build());
	}
	
	@Test
	public void testCountCategories() {
		assertEquals(8, categoryRepository.count());
	}
	
	@Test
	public void testFindCategoryByName() {
		assertEquals(false, categoryRepository.findByNameIgnoreCase("Snacks").isPresent());
		assertEquals(true, categoryRepository.findByNameIgnoreCase("Meat").isPresent());
		assertEquals(true, categoryRepository.findByNameIgnoreCase("VEGETABLES").isPresent());
	}

	@Test
	public void testSaveCategory() {
		assertEquals(false, categoryRepository.findByNameIgnoreCase("Snacks").isPresent());
		categoryRepository.save(Category.builder().name("Snacks").build());
		assertEquals(true, categoryRepository.findByNameIgnoreCase("Snacks").isPresent());
	}

	@Test
	public void testRemoveCategory() {
		Optional<Category> retval = categoryRepository.findByNameIgnoreCase("MeAt");
		assertEquals(true, retval.isPresent());
		categoryRepository.delete(retval.get());
		assertEquals(false, categoryRepository.findByNameIgnoreCase("Meat").isPresent());
	}
}
