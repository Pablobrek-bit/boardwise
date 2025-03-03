package com.henrique.pablo.BoardWise.infrastructure.persistence.repository.card;

import com.henrique.pablo.BoardWise.infrastructure.persistence.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardJpaRepository extends JpaRepository<Card, String> {
}
