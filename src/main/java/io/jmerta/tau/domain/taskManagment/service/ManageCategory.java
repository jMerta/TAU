package io.jmerta.tau.domain.taskManagment.service;

import io.jmerta.tau.domain.taskManagment.entity.Category;
import io.jmerta.tau.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManageCategory {

    private CategoryRepository categoryRepository;
    private ManageTask manageTask;

    @Autowired
    public ManageCategory(CategoryRepository categoryRepository, ManageTask manageTask) {
        this.categoryRepository = categoryRepository;
        this.manageTask = manageTask;
    }

    public void createNewCategory(Category category){
        categoryRepository.createNewCategory(category);

    }

    public void deleteCategory(Long categoryId, Long accountId){

        categoryRepository.deleteCategory(categoryId,accountId);
    }

    public List<Category> getAllCategoriesForUser(Long accountId){

        categoryRepository.getAllCategoriesForUser(accountId);
        return new ArrayList<>();
    }
}
