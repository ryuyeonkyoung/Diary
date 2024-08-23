package com.example.diary.dto;

import com.example.diary.entity.DiaryEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class DiaryDTO {
    private String diaryContents;
    private int diaryHits;
    private LocalDateTime diaryCreatedTime;
    private LocalDateTime diaryUpdatedTime;

    public DiaryDTO(LocalDateTime diaryCreatedTime) {
        this.diaryCreatedTime = diaryCreatedTime;
    }

    // entity -> dto
    public static DiaryDTO toDiaryDTO(DiaryEntity diaryEntity) {
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDiaryContents(diaryEntity.getDiaryContents());
        return diaryDTO;
    }
}
