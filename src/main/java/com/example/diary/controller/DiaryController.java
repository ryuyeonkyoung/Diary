package com.example.diary.controller;

import com.example.diary.dto.DiaryDTO;
import com.example.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor // lombok. final이나 @NonNull이 붙은 필드에 대해 생성자 자동생성
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/save")
    public String saveForm() { return "save"; }

    // save.html에서 넘어옴
    @PostMapping("/save")
    public String save(@ModelAttribute DiaryDTO diaryDTO) throws IOException {
        System.out.println("diaryDTO = " + diaryDTO);
        diaryService.save(diaryDTO);
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
        return "redirect:/diary/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute DiaryDTO diaryDTO, Model model) {
        DiaryDTO diary = diaryService.update(diaryDTO);
        model.addAttribute("diary", diary);
        return "redirect:/diary/";
    }
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<DiaryDTO> diaryList = diaryService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < diaryList.getTotalPages()) ? startPage + blockLimit - 1 : diaryList.getTotalPages(); //limit에 걸리면 보여지는 개수 조절

        model.addAttribute("diaryList", diaryList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }

}
