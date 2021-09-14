package com.yim.asocream.kind.repository;


import com.yim.asocream.kind.model.KindEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KindRepository extends JpaRepository<KindEntity,Long> {

}
