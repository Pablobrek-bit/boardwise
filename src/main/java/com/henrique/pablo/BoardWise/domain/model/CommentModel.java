package com.henrique.pablo.BoardWise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentModel {

    private String id;
    private String content;
    private LocalDateTime createdAt;
    private UserModel user;
    private CardModel card;

}
