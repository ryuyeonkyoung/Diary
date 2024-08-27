package com.example.diary.service;

import com.example.diary.dto.DiaryDTO;
import com.example.diary.entity.DiaryEntity;
import com.example.diary.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {
    // 전통적인 의존성 구조 - DiaryService가 JpaBoardRepository같은 구체적인 구현체에 의존
    // DIP - DiaryService가 추상화(인터페이스 DiaryRepository)에 의존
    // 효과 - repository에 의존하지만 JpaBoardRepository가 교체되어도 코드를 변경할 필요가 없다
    private final DiaryRepository diaryRepository;

    public void save(DiaryDTO diaryDTO) throws IOException {
        DiaryEntity diaryEntity = DiaryEntity.toSaveEntity(diaryDTO);
        diaryRepository.save(diaryEntity);
    }

    // save메소드로 update도 하고 insert도 한다.
    // update는 id save와 달리 id 필요
    // controller는 service에서 (공개)메소드만 사용. 직접 데이터베이스나 엔티티에 접근하지 않는다.
    public DiaryDTO update(DiaryDTO diaryDTO) {
        DiaryEntity diaryEntity = DiaryEntity.toUpdateEntity(diaryDTO);
        diaryRepository.save(diaryEntity);
        return findById(diaryDTO.getId()); // 업데이트된 엔티티 반환
    }

    @Transactional
    public List<DiaryDTO> findAll() {
        /*
            1. repository에서 entitiy를 리스트 형태로 가져온다.
            2. entitylist를 dtolist로 바꾼다.
            3. dtolist를 반환한다.
         */
        List<DiaryEntity> diaryEntityList = diaryRepository.findAll();
        List<DiaryDTO> diaryDTOList = new ArrayList<>();
        for (DiaryEntity diaryEntity:diaryEntityList) {
            diaryDTOList.add(DiaryDTO.toDiaryDTO(diaryEntity));
        }
        return diaryDTOList;
    }

    @Transactional
    public DiaryDTO findById(Long id) {
        // Optioinal 클래스를 통해 없을 때도 고려해서 코딩 가능
        /*
            1. repository에서 id를 찾아본다.
            2. 존재하면 entity를 받아 dto로 변환한다.
            3. dto 반환.
         */
        Optional<DiaryEntity> optionalDiaryEntity = diaryRepository.findById(id);
        if (optionalDiaryEntity.isPresent()) {
            DiaryEntity diaryEntity = optionalDiaryEntity.get();
            DiaryDTO diaryDTO = DiaryDTO.toDiaryDTO(diaryEntity);
            return diaryDTO;
        } else return null;
    }

    public void delete(Long id) {diaryRepository.deleteById(id);}

}

