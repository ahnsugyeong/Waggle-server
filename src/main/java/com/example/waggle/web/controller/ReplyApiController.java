package com.example.waggle.web.controller;

import com.example.waggle.domain.comment.entity.Reply;
import com.example.waggle.domain.comment.service.reply.ReplyCommandService;
import com.example.waggle.domain.comment.service.reply.ReplyQueryService;
import com.example.waggle.global.payload.ApiResponseDto;
import com.example.waggle.web.converter.ReplyConverter;
import com.example.waggle.web.dto.reply.ReplyRequest;
import com.example.waggle.web.dto.reply.ReplyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/replies")
@RestController
@Tag(name = "Reply API", description = "대댓글 API")
public class ReplyApiController {

    private final ReplyCommandService replyCommandService;
    private final ReplyQueryService replyQueryService;

    private Sort oldestSorting = Sort.by("createdDate").ascending();

    @Operation(summary = "대댓글 작성", description = "사용자가 대댓글을 작성합니다. 작성한 대댓글의 정보를 저장하고 댓글의 고유 ID를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "대댓글 작성 성공. 작성한 대댓글의 고유 ID를 반환합니다.")
    @ApiResponse(responseCode = "400", description = "잘못된 요청. 입력 데이터 유효성 검사 실패 등의 이유로 댓글 작성에 실패했습니다.")
    @PostMapping
    public ApiResponseDto<Long> createReply(@RequestBody ReplyRequest.Post replyWriteDto) {
        Long replyId = replyCommandService.createReply(replyWriteDto.getCommentId(), replyWriteDto);
        return ApiResponseDto.onSuccess(replyId);
    }

    @Operation(summary = "대댓글 수정", description = "사용자가 대댓글을 수정합니다. 수정한 대댓글의 정보를 저장하고 댓글의 고유 ID를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "대댓글 수정 성공. 수정한 대댓글의 고유 ID를 반환합니다.")
    @ApiResponse(responseCode = "400", description = "잘못된 요청. 입력 데이터 유효성 검사 실패 등의 이유로 댓글 작성에 실패했습니다.")
    @PutMapping("/{replyId}")
    public ApiResponseDto<Long> updateReply(@PathVariable Long replyId, @RequestBody ReplyRequest.Post replyWriteDto) {
        Long updatedReplyId = replyCommandService.updateReply(replyId, replyWriteDto);
        return ApiResponseDto.onSuccess(updatedReplyId);
    }

    @Operation(summary = "대댓글 목록 조회", description = "댓글의 대댓글 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "대댓글 목록 조회 성공. 댓글의 대댓글 목록을 반환합니다.")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @GetMapping("/{commentId}")
    public ApiResponseDto<ReplyResponse.ListDto> getReplies(@RequestParam(defaultValue = "0") int currentPage,
                                       @PathVariable Long commentId) {
        Pageable pageable = PageRequest.of(currentPage, 10, oldestSorting);
        Page<Reply> pagedReplies = replyQueryService.getPagedReplies(commentId, pageable);
        ReplyResponse.ListDto listDto = ReplyConverter.toListDto(pagedReplies);
        return ApiResponseDto.onSuccess(listDto);
    }
}