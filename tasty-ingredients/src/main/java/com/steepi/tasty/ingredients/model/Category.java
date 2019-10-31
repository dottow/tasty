package com.steepi.tasty.ingredients.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category implements Comparable<Category> {
	@Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    
    
	@Override
	public int compareTo(Category other) {
		return name.toUpperCase().compareTo(other.name.toUpperCase());
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Category other = (Category)obj;
			return name.equals(other.name);
		}
		catch (Exception e) {
			return false;
		}
	}
}

