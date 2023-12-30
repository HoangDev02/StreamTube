package com.StreamTube.services;

import java.util.List;

import com.StreamTube.models.Categories;

public interface CategoryService {
	Categories createCategory(Categories category);
	List<Categories> getAllCategories();
	Categories updateCategory(Integer id, Categories category);
	void deleteCategory(Integer id);
}
