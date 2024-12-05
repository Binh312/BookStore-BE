package com.bookstore.service;

import com.bookstore.entity.Category;
import com.bookstore.exception.GlobalException;
import com.bookstore.respository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category){
        if (category.getId() == null){
            Optional<Category> categoryOptional = categoryRepository.findCategoriesByName(category.getName());
            if (categoryOptional.isPresent()){
                throw new GlobalException("Tên danh mục này đã tồn tại");
            }
            return categoryRepository.save(category);
        } else {
            return updateCategory(category);
        }
    }

    public Category updateCategory(Category category){
        Optional<Category> categoryOptional = categoryRepository.findById(category.getId());
        if (categoryOptional.isEmpty()){
            throw new GlobalException("Danh mục này không tồn tại");
        }
        if (categoryRepository.existsByName(category.getName())){
            throw new GlobalException("Tên danh mục này đã tồn tại");
        }
        categoryRepository.save(category);
        return category;
    }

    public String deleteCategory(Long id){
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            throw new GlobalException("Danh mục này không tồn tại");
        }
        categoryRepository.delete(categoryOptional.get());
        return "Đã xóa danh mục thành công";
    }

    public Category findCategory(Long id){
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            throw new GlobalException("Danh mục này không tồn tại");
        }
        return categoryOptional.get();
    }

    public Page<Category> searchCategory(String name, Pageable pageable){
        return categoryRepository.searchCategory(name,pageable);
    }

    public Page<Category> getAllCategory(Pageable pageable){
        return categoryRepository.getAllCategory(pageable);
    }

    public List<Category> getListCategory(){
        return categoryRepository.getListCategory();
    }
}
