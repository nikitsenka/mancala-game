package com.mancala.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link GameData}.
 */
public interface GameRepository extends JpaRepository<GameData, Long> {
}
