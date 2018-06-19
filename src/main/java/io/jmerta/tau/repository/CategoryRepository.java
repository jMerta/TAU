package io.jmerta.tau.repository;

import io.jmerta.tau.domain.taskManagment.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryRepository {
    void createNewCategory(Category category);

    void getAllCategoriesForUser(Long accountId);

    void deleteCategory(Long categoryId, Long accountId);
}
