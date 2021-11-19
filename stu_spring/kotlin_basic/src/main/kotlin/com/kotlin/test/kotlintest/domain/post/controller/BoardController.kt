package com.kotlin.test.kotlintest.domain.post.controller

import com.kotlin.test.kotlintest.domain.post.payload.request.CreateBoardRequest
import com.kotlin.test.kotlintest.domain.post.service.BoardService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping("/board")
    fun saveBoard(@RequestBody @Valid boardRequest: CreateBoardRequest) {
        boardService.saveBoard(boardRequest)
    }

}