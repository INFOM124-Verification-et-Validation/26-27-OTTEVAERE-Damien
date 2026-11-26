package nl.tudelft.jpacman.npc.ghost;

import java.util.*;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * <p>
 * An implementation of the classic Pac-Man ghost Shadow.
 * </p>
 * <p>
 * Nickname: Blinky. As his name implies, Shadow is usually a constant shadow on
 * Pac-Man's tail. When he's not patrolling the top-right corner of the maze,
 * Shadow tries to find the quickest route to Pac-Man's position. Despite the
 * fact that Pinky's real name is Speedy, Shadow is actually the fastest of the
 * ghosts because of when there are only a few pellets left, Blinky drastically
 * speeds up, which can make him quite deadly. In the original Japanese version,
 * his name is Oikake/Akabei.
 * </p>
 * <p>
 * <b>AI:</b> When the ghosts are not patrolling in their home corners (Blinky:
 * top-right, Pinky: top-left, Inky: bottom-right, Clyde: bottom-left), Blinky
 * will attempt to shorten the distance between Pac-Man and himself. If he has
 * to choose between shortening the horizontal or vertical distance, he will
 * choose to shorten whichever is greatest. For example, if Pac-Man is four grid
 * spaces to the left, and seven grid spaces above Blinky, he'll try to move up
 * towards Pac-Man before he moves to the left.
 * </p>
 * <p>
 * Source: http://strategywiki.org/wiki/Pac-Man/Getting_Started
 * </p>
 *
 * @author Jeroen Roosen
 *
 */
public class Blinky extends Ghost {

    /**
     * The variation in intervals, this makes the ghosts look more dynamic and
     * less predictable.
     */
    private static final int INTERVAL_VARIATION = 50;

    /**
     * The base movement interval.
     */
    private static final int MOVE_INTERVAL = 250;

    /**
     * Incremental moveCount
     */
    public int moveCount = 12;

    /**
     * Target position for blinky patrolling
     */
    private Square blinkyTarget = null;

    /**
     * Boolean to check if the bfs was already done
     */
    private boolean bfs = false;
    /**
     * Creates a new "Blinky", a.k.a. "Shadow".
     *
     * @param spriteMap
     *            The sprites for this ghost.
     */
    // TODO Blinky should speed up when there are a few pellets left, but he
    // has no way to find out how many there are.
    public Blinky(Map<Direction, Sprite> spriteMap) {
        super(spriteMap, MOVE_INTERVAL, INTERVAL_VARIATION);
    }

    /**
     * Performs a bfs in every possible direction
     * @param current = spanw point of blinky
     * @return visited=list of all the visited Squares
     */
    public Set<Square> getBlinkyTarget(Square current){
        Set<Square> visited = new HashSet<>();
        Queue<Square> toVisit = new ArrayDeque<>();
        visited.add(current);
        toVisit.add(current);
        while (!toVisit.isEmpty()) {
            Square square = toVisit.remove();
            for(Direction direction : Direction.values()) {
                if(isAccesibleToBlinky(square, direction)){
                    Square target = square.getSquareAt(direction);
                    if(target != null && visited.add(target)) {
                        toVisit.add(target);
                    }
                }
            }
        }
        return visited;
    }

    /**
     * Checks whether a given square is accessible
     * FOR blinky FROM a given direction
     * @param position actual square to check
     * @param direction
     * @return boolean
     */
    public boolean isAccesibleToBlinky(Square position, Direction direction){
        return position.getSquareAt(direction).isAccessibleTo(this);
    }

    /**
     * Performs research in the computed list of path
     * and updates the blinkyTarget
     */
    public void findTopSquare(){
        Set<Square> squares = getBlinkyTarget(getSquare());
        for(Square square : squares) {
            blinkyTarget = square;
        }
        bfs = true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * When the ghosts are not patrolling in their home corners (Blinky:
     * top-right, Pinky: top-left, Inky: bottom-right, Clyde: bottom-left),
     * Blinky will attempt to shorten the distance between Pac-Man and himself.
     * If he has to choose between shortening the horizontal or vertical
     * distance, he will choose to shorten whichever is greatest. For example,
     * if Pac-Man is four grid spaces to the left, and seven grid spaces above
     * Blinky, he'll try to move up towards Pac-Man before he moves to the left.
     * </p>
     */
    @Override
    public Optional<Direction> nextAiMove() {
        assert hasSquare();

        if(!bfs) {findTopSquare();}

        if(0 < moveCount && moveCount <= 8){
            moveCount--;

            Unit nearest = Navigation.findNearest(Player.class, getSquare());
            if (nearest == null) {
                return Optional.empty();
            }
            assert nearest.hasSquare();
            Square target = nearest.getSquare();

            List<Direction> path = Navigation.shortestPath(getSquare(), target, this);
            if (path != null && !path.isEmpty()) {
                return Optional.ofNullable(path.get(0));
            }
        } else if (8 < moveCount && moveCount <= 12) {
            moveCount--;

            Square target = blinkyTarget;

            List<Direction> path = Navigation.shortestPath(getSquare(), target, this);
            if (path != null && !path.isEmpty()) {
                return Optional.ofNullable(path.get(0));
            }
        }
        if(moveCount==0){moveCount = 12;}

        return Optional.empty();
    }
}
