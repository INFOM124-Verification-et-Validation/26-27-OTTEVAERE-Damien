package nl.tudelft.jpacman.npc.ghost;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ClydeTest {

    PacManSprites pacManSprites = new PacManSprites();
    GhostFactory ghostFactory = new GhostFactory(pacManSprites);
    LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory);
    BoardFactory boardFactory = new BoardFactory(pacManSprites);
    GhostMapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);

    /**
     * si clyde à moins de 8 case, se rapproche, aussi non s'éloigne
     *
     * instancier une carte, clyde et un joueur, puis tester
     */
    @Test
    void directionTest(){

        /* trouver la position de clyde et du joueur*/
        Ghost clyde = ghostFactory.createClyde();
        Pellet pellet = levelFactory.createPellet();

        /* map init */
        PacManSprites sprites = new PacManSprites();
        GhostMapParser parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        Board b = parser.parseMap(Lists.newArrayList("###########", "#   C     #", "#    #    #", "# ##      #", "#     ### #", "#     P   #", "###########")).getBoard();

        Square clydePos = b.squareAt(4,1);
        Direction d  = clyde.nextMove();

        System.out.println(clydePos.getOccupants());
        System.out.println(d);


    }


}
