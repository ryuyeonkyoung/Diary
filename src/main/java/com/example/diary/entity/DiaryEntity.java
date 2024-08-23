package com.example.diary.entity;

import com.example.diary.dto.DiaryDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "diary_table")
public class DiaryEntity extends BaseEntity{
    @Id // 이 필드가 엔티티의 기본 키임을 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String diaryContents;


    public static DiaryEntity toSaveEntity(DiaryDTO diaryDTO) {
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setId(diaryDTO.getId());
        diaryEntity.setDiaryContents(diaryDTO.getDiaryContents());
        return diaryEntity;
    }

    // dto -> entity
    public static DiaryEntity toUpdateEntity(DiaryDTO diaryDTO) {
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setId(diaryDTO.getId()); //단순 save와 다르게 id가 필요
        diaryEntity.setDiaryContents(diaryDTO.getDiaryContents());
        return diaryEntity;
    }
}
