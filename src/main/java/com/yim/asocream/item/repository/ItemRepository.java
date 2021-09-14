package com.yim.asocream.item.repository;

import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.kind.model.KindEntity;
import com.yim.asocream.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity,Long> {
    List<ItemEntity> findOptionalByKindEntity(KindEntity kindEntity);
}
