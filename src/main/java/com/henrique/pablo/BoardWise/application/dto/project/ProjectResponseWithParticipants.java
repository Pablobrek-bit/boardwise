package com.henrique.pablo.BoardWise.application.dto.project;

import com.henrique.pablo.BoardWise.application.dto.user.UserSimpleReturn;

import java.util.Set;

public record ProjectResponseWithParticipants(
        String id,
        String name,
        String description,
        String ownerId,
        Set<UserSimpleReturn> participants
) {
}
