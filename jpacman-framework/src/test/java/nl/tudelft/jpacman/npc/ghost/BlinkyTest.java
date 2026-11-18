package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BlinkyTest{
    GhostMapParser ghostMapParser;
    LevelFactory levelFactory;
    BoardFactory boardFactory;
    GhostFactory ghostFactory;
    PacManSprites pacManSprites;
    Player player;

    @BeforeEach
    public void setup() {
        this.pacManSprites = new PacManSprites();
        this.boardFactory = new BoardFactory(pacManSprites);
        this.ghostFactory = new GhostFactory(pacManSprites);
        this.levelFactory = new LevelFactory(pacManSprites, ghostFactory);
        this.ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        player = new PlayerFactory(pacManSprites).createPacMan();
    }

    /**
     * expected: va à gauche pour se rapprocher
     */
    @Test
    public void blinkyNextToPacman(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P        #");
        map.add("# #  #    #");
        map.add("#    #    #");
        map.add("#   # #  ##");
        map.add("#G        #");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        Optional<Direction> actualDir = blinky.nextAiMove();
        Optional<Direction> expectedDir = Optional.of(Direction.NORTH);

        assertThat(actualDir).isEqualTo(expectedDir);
    }
    /**
     * expected: va à gauche pour se rapprocher
     */
    @Test
    public void blinkyHomeCorner(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P       G#");
        map.add("# #  #    #");
        map.add("#    #    #");
        map.add("#   # #  ##");
        map.add("#         #");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        Optional<Direction> actualDir = blinky.nextAiMove();
        Optional<Direction> expectedDir = Optional.of(Direction.WEST);

        assertThat(actualDir).isEqualTo(expectedDir);
    }

    @Test
    public void blinkyStuck(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P        #");
        map.add("# # ##    #");
        map.add("#  #G#    #");
        map.add("#   # #  ##");
        map.add("#         #");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        Optional<Direction> actualDir = blinky.nextAiMove();
        Optional<Direction> expectedDir = Optional.empty();

        assertThat(actualDir).isEqualTo(expectedDir);
    }
}
