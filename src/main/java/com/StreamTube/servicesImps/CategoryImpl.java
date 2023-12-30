package com.StreamTube.servicesImps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StreamTube.models.Categories;
import com.StreamTube.repositories.categoriesRepository;
import com.StreamTube.services.CategoryService;

@Service
public class CategoryImpl implements CategoryService{
	
	@Autowired
	private categoriesRepository categoriesRepository;
	
	@Override
	public Categories createCategory(Categories category) {
	
		return categoriesRepository.save(category);
	}

	@Override
	public List<Categories> getAllCategories() {
		// TODO Auto-generated method stub
		return categoriesRepository.findAll();
	}

	 @Override
	    public Categories updateCategory(Integer id, Categories category) {
	        return categoriesRepository.findById(id)
	                .map(existingCategory -> {
	                     existingCategory.setName(category.getName());
	                    return categoriesRepository.save(existingCategory);
	                })
	                .orElse(null);
	    }

	 @Override
		public void deleteCategory(Integer id) {
			categoriesRepository.deleteById(id);
		}

}
