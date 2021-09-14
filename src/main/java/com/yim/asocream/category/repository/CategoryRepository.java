package com.yim.asocream.category.repository;

import com.yim.asocream.category.model.CategoryEntity;
import com.yim.asocream.order.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

}
