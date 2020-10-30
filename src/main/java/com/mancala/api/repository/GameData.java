package com.mancala.api.repository;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Persistence store for games.
 */
@Data
@Entity
@Table(name = "games")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class GameData {

    /**
     * Unique identifier of the game.
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Type(type = "list-array")
    @Column(

            columnDefinition = "integer[]"
    )
    private List<Integer> pits;
    private Integer currentPlayer;
}
