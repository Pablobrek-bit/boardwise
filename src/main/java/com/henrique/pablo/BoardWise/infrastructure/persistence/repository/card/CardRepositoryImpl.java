package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.card;

import com.henrique.pablo.BoardWise.domain.repository.ICardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CardRepositoryImpl implements ICardRepository {

    private final CardJpaRepository cardJpaRepository;

}
