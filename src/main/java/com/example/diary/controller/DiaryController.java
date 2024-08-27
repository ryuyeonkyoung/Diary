package com.example.diary.controller;

import com.example.diary.dto.DiaryDTO;
import com.example.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/")
    public String findAll(Model model) {
        /*
            controller에서는 service의 메소드를 이용한다.
            entity, dto, repository에 실제로 접근할 수 있는건 controller가 아니라 service이다.
         */
        List<DiaryDTO> diaryDTOList = diaryService.findAll();
        model.addAttribute("diaryList", diaryDTOList);
        return "list"; // list.html에 dtolist보냄
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        diaryService.delete(id);
        return "redirect:/board/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute DiaryDTO diaryDTO, Model model) {
        DiaryDTO diary = diaryService.update(diaryDTO);
        model.addAttribute("diary", diary);
        return "redirect:/board/";
    }

}
