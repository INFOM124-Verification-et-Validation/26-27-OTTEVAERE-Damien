package nl.tudelft.jpacman.level;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
/**
 * Test suite to confirm that {@link Score} correctly stores and
 * accumulates points.
 */
class ScoreTest {

    /**
     * The score under test.
     */
    private Score score;

    /**
     * Resets the score under test.
     */
    @BeforeEach
    void setUp() {
        score = new Score(0);
    }

    /**
     * Asserts that a freshly created score starts at the given amount of
     * points.
     */
    @Test
    void testInitialPoints() {
        assertThat(score.getPoints()).isEqualTo(0);
    }

    /**
     * Asserts that adding points increases the total by that amount.
     */
    @Test
    void testAddPoints() {
        score.addPoints(1);
        assertThat(score.getPoints()).isEqualTo(1);

    }

    /**
     * Asserts that adding points twice accumulates both amounts.
     */
    @Test
    void testAddPointsTwice() {
        int point1 = 12;
        int point2 = 13;

        score.addPoints(point1);
        score.addPoints(point2);

        assertThat(score.getPoints()).isEqualTo(point1+point2);
    }

    /**
     * Asserts that adding negative points decreases the total by that amount.
     */
    @Test
    void testAddNegativePoints() {
        int point1 = 50;
        int point2 = -13;

        score.addPoints(point1);
        score.addPoints(point2);

        assertThat(score.getPoints()).isEqualTo(point1+point2);
    }
}
