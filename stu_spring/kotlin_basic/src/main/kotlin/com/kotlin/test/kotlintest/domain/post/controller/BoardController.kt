package com.kotlin.test.kotlintest.domain.post.controller

import com.kotlin.test.kotlintest.domain.post.payload.request.CreateBoardRequest
import com.kotlin.test.kotlintest.domain.post.payload.response.BoardContentResponse
import com.kotlin.test.kotlintest.domain.post.service.BoardService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping("/board")
    fun saveBoard(@RequestBody @Valid boardRequest: CreateBoardRequest) {
        boardService.saveBoard(boardRequest)
    }

    @GetMapping("/board/{id}")
    fun saveBoard(@PathVariable id: Long) : BoardContentResponse{
        return boardService.getOneBoard(id)
    }

}