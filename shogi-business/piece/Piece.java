package piece;

import java.util.HashSet;
import java.util.Set;

import pack.Color;
import pack.Coordinates;
import board.Board;

/**
 * Base class for all Shogi pieces.
 * Encapsulates common state (color, coordinates) and movement logic.
 */
public abstract class Piece {
    private final Color color;
    private Coordinates coordinates;

    /**
     * Constructs a piece with a specific color and initial coordinates.
     */
    public Piece(Color color, Coordinates coordinates) {
        this.color = color;
        this.coordinates = coordinates;
    }

    /**
     * @return The color of this piece (SENTE or GOTE).
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return Current coordinates of the piece on the board.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Updates the piece's position.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Calculates all squares where the piece can legally move.
     * Logic: iterates through piece shifts, checks board boundaries and square availability.
     */
    public Set<Coordinates> getAvailableMoveSquares(Board board) {
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift : getPieceMoves()) {
            if (getCoordinates().canShift(shift)) {
                Coordinates newCoordinates = getCoordinates().shift(shift);

                if (isSquareAvailableForMove(newCoordinates, board)) {
                    result.add(newCoordinates);
                }
            }
        }

        return result;
    }

    /**
     * Determines if a target square is valid for a move (empty or occupied by an enemy).
     */
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Board board) {
        return board.isSquareEmpty(coordinates) || board.getPiece(coordinates).getColor() != getColor();
    }

    /**
     * To be implemented by subclasses to define specific movement vectors.
     */
    protected abstract Set<CoordinatesShift> getPieceMoves();

    /**
     * Returns the patterns used for attacking. Defaults to movement patterns.
     */
    protected Set<CoordinatesShift> getPieceAttacks() {
        return getPieceMoves();
    }

    /**
     * Calculates all squares currently threatened by this piece.
     */
    public Set<Coordinates> getAttackedSquares(Board board) {
        Set<CoordinatesShift> pieceAttacks = getPieceAttacks();
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift pieceAttack : pieceAttacks) {
            if (getCoordinates().canShift(pieceAttack)) {
                Coordinates shiftedCoordinates = getCoordinates().shift(pieceAttack);

                if (isSquareAvailableForAttack(shiftedCoordinates, board)) {
                    result.add(shiftedCoordinates);
                }
            }
        }

        return result;
    }

    /**
     * Basic check for attack availability. 
     * Can be overridden for complex pieces (e.g., long-range).
     */
    protected boolean isSquareAvailableForAttack(Coordinates coordinates, Board board) {
        return true;
    }
}