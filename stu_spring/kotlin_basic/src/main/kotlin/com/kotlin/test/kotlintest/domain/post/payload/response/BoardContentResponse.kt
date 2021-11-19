package com.kotlin.test.kotlintest.domain.post.payload.response

import java.time.LocalDateTime

class BoardContentResponse(
    val title: String,
    val content: String,
    val writerName: String,
    val createdAt: LocalDateTime,
)