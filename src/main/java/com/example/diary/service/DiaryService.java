package com.example.diary.service;

import com.example.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    // 전통적인 의존성 구조 - DiaryService가 JpaBoardRepository같은 구체적인 구현체에 의존
    // DIP - DiaryService가 추상화(인터페이스 DiaryRepository)에 의존
    // 효과 - repository에 의존하지만 JpaBoardRepository가 교체되어도 코드를 변경할 필요가 없다
    private final DiaryRepository diaryRepository;


}

