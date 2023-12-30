package com.StreamTube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StreamTube.configs.CustomUserDetails;
import com.StreamTube.dtos.CategoriesDTO;
import com.StreamTube.mappers.CategoriesMapper;
import com.StreamTube.models.Categories;
import com.StreamTube.models.User;
import com.StreamTube.services.CategoryService;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoriesMapper categoriesMapper;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public List<CategoriesDTO> getAllCategories() {
		return categoriesMapper.toDTOList(categoryService.getAllCategories());
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createCategory(@RequestBody CategoriesDTO categoriesDTO){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
		User currentUser = currentUserDetails.getUser();
		Categories categories = categoriesMapper.toMoDel(categoriesDTO);
		categoryService.createCategory(categories);
		 return ResponseEntity.ok("Create channel Success");
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoriesDTO> updatecategory(@PathVariable("id") Integer id, @RequestBody CategoriesDTO categoriesDTO){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUserDetails currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
	    Integer userId = currentUserDetails.getUser().getId();
	    
	    Categories updateCategory = categoryService.updateCategory(id, categoriesMapper.toMoDel(categoriesDTO));
	    CategoriesDTO updateDTO = categoriesMapper.toDTO(updateCategory);
	    return ResponseEntity.ok(updateDTO);
	}
	@DeleteMapping("{id}")
	public ResponseEntity<CategoriesDTO> deleteCategory(@PathVariable("id") Integer id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUserDetails currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
	    
	    categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}
}
