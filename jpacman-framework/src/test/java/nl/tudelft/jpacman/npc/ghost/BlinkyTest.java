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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlinkyTest{
    GhostMapParser ghostMapParser;
    LevelFactory levelFactory;
    BoardFactory boardFactory;
    GhostFactory ghostFactory;
    PacManSprites pacManSprites;
    Player player;

    @BeforeEach
    void setup() {
        this.pacManSprites = new PacManSprites();
        this.boardFactory = new BoardFactory(pacManSprites);
        this.ghostFactory = new GhostFactory(pacManSprites);
        this.levelFactory = new LevelFactory(pacManSprites, ghostFactory);
        this.ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        player = new PlayerFactory(pacManSprites).createPacMan();
    }

    /**
     * expected: blinky monte pour se rapprocher
     */
    @Test
    void blinkyNextToPacman(){
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

        // chase mode
        blinky.moveCount = 8;
        Optional<Direction> actualDir = blinky.nextAiMove();
        Optional<Direction> expectedDir = Optional.of(Direction.NORTH);

        assertThat(actualDir).isEqualTo(expectedDir);
    }
    /**
     * expected: va à gauche pour se rapprocher
     */
    @Test
    void blinkyHomeCorner(){
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

        // chase mode
        blinky.moveCount = 8;
        Optional<Direction> actualDir = blinky.nextAiMove();
        Optional<Direction> expectedDir = Optional.of(Direction.WEST);

        assertThat(actualDir).isEqualTo(expectedDir);
    }

    @Test
    void blinkyStuck(){
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

    /**
     * Tests if moveCount goes from 12 to 0
     */
    @Test
    void testMoveCount(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P       G#");
        map.add("# # #     #");
        map.add("#  #      #");
        map.add("#   # #  ##");
        map.add("#         #");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        assertEquals(12, blinky.moveCount);

        for(int i=12;i>=0;i--){
            assertEquals(i, blinky.moveCount);
            blinky.nextAiMove();
        }
        assertEquals(12, blinky.moveCount);

    }

    /**
     * Tests wheter blinky is patrolling
     * = moving toward the top of the map for
     * 4 move in a row
     */
    @Test
    void blinkyPatrolling(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P   #    #");
        map.add("#### # ## #");
        map.add("#  #   ## #");
        map.add("#   ### # #");
        map.add("#       #G#");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        //patroll mode
        blinky.moveCount=12;
        Optional<Direction> actualDir = blinky.nextAiMove();
        Optional<Direction> expectedDir = Optional.of(Direction.NORTH);

        assertThat(expectedDir).isEqualTo(actualDir);
    }

    /**
     * Test wheter blinky is chasing
     * = moving toward pacman for
     * 8 move in a row
     */
    @Test
    void blinkyChassing(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P   #   G#");
        map.add("#### # ## #");
        map.add("#  #   ## #");
        map.add("#   ### # #");
        map.add("#         #");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        //chase mode
        blinky.moveCount = 8;
        
        Optional<Direction> actualDir = blinky.nextAiMove();
        Optional<Direction> expectedDir = Optional.of(Direction.WEST);

        assertThat(expectedDir).isEqualTo(actualDir);
    }

    /**
     * test the behaviour of nextAiMove when no player on the map
     */
    @Test
    void nextAiMoveNoPlayer(){
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#    #   G#");
        map.add("#### # ## #");
        map.add("#  #   ## #");
        map.add("#   ### # #");
        map.add("#         #");
        map.add("###########");

        Level level = ghostMapParser.parseMap(map);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        //chase mode
        blinky.moveCount = 8;

        Optional<Direction> actualDir = blinky.nextAiMove();
        Optional<Direction> expectedDir = Optional.empty();

        assertThat(expectedDir).isEqualTo(actualDir);
    }

    /**
     * test the behaviour of nextAiMove when blinky has no square
     */
    @Test
    void blinkyHasNoSquare(){
        List<String> map = new ArrayList<>();
        map.add("PG");

        Level level = ghostMapParser.parseMap(map);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        blinky.leaveSquare();

        //chase mode
        blinky.moveCount = 8;

        //assert que nextAiMove renvoit une erreur
        assertThatThrownBy(blinky::nextAiMove).isInstanceOf(AssertionError.class);
    }

    /**
     * test the behaviour of nextAiMove when player has no square
     */
    @Test
    void nearestIsNull(){
        List<String> map = new ArrayList<>();
        map.add("PG");

        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        player.leaveSquare();

        //chase mode
        blinky.moveCount = 5;
        Optional<Direction> d = blinky.nextAiMove();
        assertEquals(Optional.empty(), d);
    }

    /**
     * Test the behavior of path in chase mode
     */
    @Test
    void pathIsNullChaseMode(){
        List<String> map = new ArrayList<>();
        map.add("P  ");
        map.add("###");
        map.add("#G#");
        map.add("###");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        //chase mode
        blinky.moveCount = 5;
        Optional<Direction> d = blinky.nextAiMove();
        assertEquals(Optional.empty(), d);
    }

    /**
     * Test the behavior of path in patroll mode
     */
    @Test
    void pathIsEmptyPatrollMode(){
        List<String> map = new ArrayList<>();
        map.add("G");
        Level level = ghostMapParser.parseMap(map);
        Blinky blinky = Navigation.findUnitInBoard(Blinky.class, level.getBoard());
        //patroll mode
        blinky.moveCount = 12;
        Optional<Direction> d = blinky.nextAiMove();
        assertEquals(Optional.empty(), d);
    }



}
