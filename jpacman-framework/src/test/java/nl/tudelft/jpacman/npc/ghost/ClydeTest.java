package nl.tudelft.jpacman.npc.ghost;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClydeTest {
    PacManSprites sprites;
    GhostFactory ghostFactory;
    LevelFactory levelFactory;
    BoardFactory boardFactory;
    GhostMapParser ghostMapParser;
    Player player;
    MapParser mapParser;
    PlayerFactory playerFactory;

    @BeforeEach
    void setUp() {
        this.sprites = new PacManSprites();
        this.ghostFactory = new GhostFactory(sprites);
        this.levelFactory = new LevelFactory(sprites, ghostFactory);
        this.boardFactory = new BoardFactory(sprites);
        this.ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        this.mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        this.playerFactory = new PlayerFactory(sprites);
        player = new PlayerFactory(sprites).createPacMan();
    }

    /**
     * si clyde à moins de 8 case, s'éloigne, aussi non se rapproche
     */
    @Test
    void clydeNextToPacman(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P    #   #");
        map.add("#C#  #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#         #");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        Optional<Direction> actualDir = clyde.nextAiMove();
        Optional<Direction> expectedDir = Optional.of(Direction.SOUTH);

        assertThat(actualDir).isEqualTo(expectedDir);
    }
    @Test
    void clydeFarFromPacman(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P    #   #");
        map.add("# #  #    #");
        map.add("#    #    #");
        map.add("#   # #  ##");
        map.add("#        C#");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        Optional<Direction> actualDir = clyde.nextAiMove();
        Optional<Direction> expectedDir = Optional.of(Direction.WEST);

        assertThat(actualDir).isEqualTo(expectedDir);
    }
    @Test
    void clydeStuck(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P    #   #");
        map.add("# #  #    #");
        map.add("#    #    #");
        map.add("##  # #   #");
        map.add("#C#       #");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        Optional<Direction> actualDir = clyde.nextAiMove();
        Optional<Direction> expectedDir = Optional.empty();

        assertThat(actualDir).isEqualTo(expectedDir);
    }


}
