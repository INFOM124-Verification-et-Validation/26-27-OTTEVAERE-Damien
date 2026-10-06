package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.tudelft.jpacman.board.Direction.EAST;
import static nl.tudelft.jpacman.board.Direction.WEST;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test suite to confirm that {@link Square}s correctly link to and report
 * their neighbouring squares.
 */
class SquareNeighbourTest {

    /**
     * The square under test.
     */
    private Square square;

    /**
     * A neighbouring square used to test linking.
     */
    private Square neighbour;

    /**
     * Resets the squares under test.
     */
    @BeforeEach
    void setUp() {
        square = new BasicSquare();
        neighbour = new BasicSquare();
    }

    /**
     * Asserts that a square has no neighbour in a given direction before it
     * has been linked to one.
     */
    @Test
    void testNoNeighbourByDefault() {
        Direction d = EAST;
        assertThat(square.getSquareAt(d)).isEqualTo(null);
    }

    /**
     * Asserts that after linking a square to a neighbour, that neighbour can
     * be retrieved in the given direction.
     */
    @Test
    void testLinkedNeighbour() {
        Direction d = EAST;
        square.link(neighbour, d);

        assertThat(square.getSquareAt(d)).isEqualTo(neighbour);
    }

    /**
     * Asserts that linking a square is a one-way connection: linking square
     * to neighbour does not automatically link neighbour back to square.
     */
    @Test
    void testLinkIsOneWay() {
        Direction d = EAST;
        Direction reverse = WEST;
        square.link(neighbour, d);

        assertThat(square.getSquareAt(d)).isEqualTo(neighbour);
        assertThat(neighbour.getSquareAt(reverse)).isNotEqualTo(square);
    }
}
