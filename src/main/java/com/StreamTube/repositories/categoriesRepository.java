package com.StreamTube.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StreamTube.models.Categories;

public interface categoriesRepository extends JpaRepository<Categories, Integer> {
		
}
