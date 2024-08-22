package com.example.diary.controller;

import com.example.diary.dto.DiaryDTO;
import com.example.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor // lombok. final이나 @NonNull이 붙은 필드에 대해 생성자 자동생성
@RequestMapping("/board")
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/save")
    public String saveForm() { return "save"; }

    // save.html에서 넘어옴
    @PostMapping("/save")
    public String save(@ModelAttribute DiaryDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        diaryService.save(boardDTO);
        return "index";
    }
}
