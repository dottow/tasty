package com.steepi.tasty.ingredients.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Ingredient implements Comparable<Ingredient> {
	@Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Category category;
    
	public int compareTo(Ingredient other) {
		int categoryCompare = category.compareTo(other.category);
		if (categoryCompare != 0) {
			return categoryCompare;
		}
		return name.compareTo(other.name);
	}
}

