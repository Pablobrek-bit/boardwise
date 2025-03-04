package com.henrique.pablo.BoardWise.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardModel {

    private String id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String priority;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BoardListModel boardList;
    private UserModel assignee;
    private Set<CommentModel> comments;
}
