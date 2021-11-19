package com.kotlin.test.kotlintest.domain.post.service

import com.kotlin.test.kotlintest.domain.post.entity.Board
import com.kotlin.test.kotlintest.domain.post.entity.BoardRepository
import com.kotlin.test.kotlintest.domain.post.payload.request.CreateBoardRequest
import com.kotlin.test.kotlintest.domain.user.entity.User
import com.kotlin.test.kotlintest.domain.user.entity.UserFacade
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userFacade: UserFacade
){
    fun saveBoard(boardRequest: CreateBoardRequest) {
        val user = userFacade.getCurrentUser()
        val board = buildBoard(boardRequest, user)
        boardRepository.save(board)
    }

    private fun buildBoard(boardRequest: CreateBoardRequest, writer: User): Board {
        return Board(
            title = boardRequest.title,
            content = boardRequest.content,
            user = writer
        )
    }
}