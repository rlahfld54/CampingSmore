package com.green.campingsmore.community.board;

import com.green.campingsmore.community.board.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "게시판")
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService service;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "게시판 사진 다중 업로드")
    public Long post(@RequestPart BoardInsDto dto, @RequestPart(required = false) List<MultipartFile> pics) throws Exception {
        return service.postBoard(dto, pics);
    }

    @GetMapping("/{iuser}")
    @Operation(summary = "내가 작성한글 보기")
    public List<BoardMyVo> selMyBoard(@PathVariable Long iuser) {
        BoardMyDto dto = new BoardMyDto();
        dto.setIuser(iuser);
        return service.selMyBoard(dto);
    }

    @PutMapping
    @Operation(summary = "게시글 삭제 하기")
    public Long delBoard(@RequestBody BoardDelDto dto) {
        return service.delBoard(dto);
    }

    @GetMapping("/comunity")
    @Operation(summary = "게시글 리스트 보기")
    public BoardRes selBoardList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") @Min(value = 15) int row) {
        BoardPageDto dto = new BoardPageDto();
        dto.setPage(page);
        dto.setRow(row);
        return service.selBoardList(dto);
    }

    @GetMapping("/icategory")
    @Operation(summary = "카테고리별 게시글 리스트 보기")
    public BoardRes categoryBoardList(@RequestParam Long icategory,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") @Min(value = 15) int row) {
        BoardPageDto dto = new BoardPageDto();
        dto.setIcategory(icategory);
        dto.setPage(page);
        dto.setRow(row);
        return service.categoryBoardList(dto);

    }
}
