package com.example.waggle.board.story.controller;

import com.example.waggle.commons.security.SecurityUtil;
import com.example.waggle.board.story.dto.StoryViewDto;
import com.example.waggle.board.story.dto.StorySimpleViewDto;
import com.example.waggle.board.story.dto.StoryWriteDto;
import com.example.waggle.member.dto.MemberSummaryDto;
import com.example.waggle.board.story.service.StoryService;
import com.example.waggle.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/story")
public class StoryController {


    private final StoryService storyService;
    private final MemberService memberService;

    /**
     * view
     */
    @GetMapping
    public String storyMain(Model model) {
        /*
        List<StorySimpleViewDto> storySimpleViewDtos = storyService.findAll();
        model.addAttribute("storySimpleViewDtos", storySimpleViewDtos);
        model.addAttribute("simpleStories", allStory);
        */
        return "story/main";
    }


    @GetMapping("/{username}")
    public String memberStory(@PathVariable String username,
                              Model model) {
        List<StorySimpleViewDto> allStoryByMember = storyService.findAllStoryByUsername(username);
        model.addAttribute("simpleStories", allStoryByMember);
        return "story/memberStory";
    }

    @GetMapping("/{username}/{boardId}")
    public String singleStoryForm(@PathVariable String username,
                                  @PathVariable Long boardId,
                                  Model model) {
        StoryViewDto storyByBoardId = storyService.findStoryViewByBoardId(boardId);
        model.addAttribute("storyDto", storyByBoardId);
        return "story/story";
    }

    /**
     * write
     */
    @GetMapping("/write")
    public String singleStoryWriteForm(Model model) {
        String username = SecurityUtil.getCurrentUsername();
        MemberSummaryDto memberSummaryDto = memberService.getMemberSummaryDto(username);
        StoryWriteDto storyDto = new StoryWriteDto();

        model.addAttribute("storyDto", storyDto);
        model.addAttribute("memberSimpleDto", memberSummaryDto);

        return "story/writeStory";
    }

    @PostMapping("/write")
    public String singleStoryWrite(@Validated @ModelAttribute StoryWriteDto storyDto,
                                   BindingResult bindingResult) {
        //validation
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "story/writeStory";
        }

        String username = SecurityUtil.getCurrentUsername();
        Long boardId = storyService.saveStory(storyDto);
        return "redirect:/story/" + username + "/" + boardId;
    }

    @GetMapping("/edit/{boardId}")
    public String singleStoryEditForm(Model model, @PathVariable Long boardId) {
//        if (storyDto.getUsername() != SecurityUtil.getCurrentUsername()) {
//            // 작성자 외의 접근 error 처리
//        }
        if (!storyService.checkMember(boardId)) {
            //error
            return "redirect:/story";
        }
        StoryWriteDto storyDto = storyService.findStoryWriteByBoardId(boardId);
        MemberSummaryDto memberSummaryDto = memberService.getMemberSummaryDto(SecurityUtil.getCurrentUsername());
        model.addAttribute("storyDto", storyDto);
        model.addAttribute("profileImg", memberSummaryDto.getProfileImg());

        return "story/editStory";
    }

    @PostMapping("/edit/{boardId}")
    public String singleStoryEdit(@ModelAttribute StoryWriteDto storyDto,
                                  @PathVariable Long boardId) {
        String username = storyService.changeStory(storyDto);
        return "redirect:/story/" + username + "/" + boardId;
    }


    /**
     * remove
     */
}
