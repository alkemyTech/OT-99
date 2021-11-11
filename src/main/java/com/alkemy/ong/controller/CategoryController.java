package com.alkemy.ong.controller;

import java.util.List;

import javax.validation.Valid;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoGetAll;
import com.alkemy.ong.exception.DataAlreadyExistException;
import com.alkemy.ong.exception.NotFoundException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
   
    @Autowired
    private CategoryService categoryService;

    @GetMapping
	public ResponseEntity<List<CategoryDtoGetAll>> getAllCategories(){

		return new ResponseEntity<>( categoryService.getAllCategories() , HttpStatus.OK );
	}

    @PostMapping("/create")
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryDto categoryDto) throws DataAlreadyExistException {
        Category category = categoryService.save(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Category> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(categoryService.update(id, categoryDto), HttpStatus.OK);
    }
}
