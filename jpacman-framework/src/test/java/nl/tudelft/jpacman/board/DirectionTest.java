package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;
import static nl.tudelft.jpacman.board.Direction.*;

import org.junit.jupiter.api.Test;

/**
 * A very simple (and not particularly useful)
 * test class to have a starting point where to put tests.
 *
 * @author Arie van Deursen
 */
class DirectionTest {

    /**
     * Do we get the correct delta when moving north?
     */
    @Test
    void testNorth() {
        Direction d = NORTH;
        assertThat(d).isEqualTo(NORTH);
    }

    @Test
    void testSouth() {
        Direction d = SOUTH;
        assertThat(d).isEqualTo(SOUTH);
    }

    @Test
    void testEast() {
        Direction d = EAST;
        assertThat(d).isEqualTo(EAST);
    }

    @Test
    void testWest() {
        Direction d = WEST;
        assertThat(d).isEqualTo(WEST);
    }
}
