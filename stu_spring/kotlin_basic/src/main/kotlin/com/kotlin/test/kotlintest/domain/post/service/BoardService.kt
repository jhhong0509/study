package com.kotlin.test.kotlintest.domain.post.service

import com.kotlin.test.kotlintest.domain.post.entity.Board
import com.kotlin.test.kotlintest.domain.post.entity.BoardRepository
import com.kotlin.test.kotlintest.domain.post.exceptions.BoardNotFoundException
import com.kotlin.test.kotlintest.domain.post.payload.request.CreateBoardRequest
import com.kotlin.test.kotlintest.domain.post.payload.response.BoardContentResponse
import com.kotlin.test.kotlintest.domain.user.entity.User
import com.kotlin.test.kotlintest.domain.user.entity.UserFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userFacade: UserFacade
) {
    fun saveBoard(boardRequest: CreateBoardRequest) {
        val user = userFacade.getCurrentUser()
        val board = buildBoard(boardRequest, user)
        boardRepository.save(board)
    }

    fun getOneBoard(id: Long) : BoardContentResponse{
        val userBoard = boardRepository.findByIdOrNull(id) ?: throw BoardNotFoundException.EXCEPTION
        return userBoard.let { board ->
            BoardContentResponse(
                title = board.title,
                content = board.content,
                createdAt = board.createdAt ?: LocalDateTime.now(),
                writerName = board.user.name
            )
        }
    }

    private fun buildBoard(boardRequest: CreateBoardRequest, writer: User): Board {
        return Board(
            title = boardRequest.title,
            content = boardRequest.content,
            user = writer
        )
    }
}