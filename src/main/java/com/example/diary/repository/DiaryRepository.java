package com.example.diary.repository;

import com.example.diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//<T(Entity), id 변수타입>
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

}
