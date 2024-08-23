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
    private Long id;
    private String diaryContents;
    private LocalDateTime diaryCreatedTime;
    private LocalDateTime diaryUpdatedTime;

    public DiaryDTO(Long id, LocalDateTime diaryCreatedTime) {
        this.id = id;
        this.diaryCreatedTime = diaryCreatedTime;
    }

    // entity -> dto
    public static DiaryDTO toDiaryDTO(DiaryEntity diaryEntity) {
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setId(diaryEntity.getId());
        diaryDTO.setDiaryContents(diaryEntity.getDiaryContents());
        diaryDTO.setDiaryCreatedTime(diaryEntity.getCreatedTime());
        diaryDTO.setDiaryUpdatedTime(diaryEntity.getUpdatedTime());
        return diaryDTO;
    }

}
