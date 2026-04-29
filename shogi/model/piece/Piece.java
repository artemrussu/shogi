package model.piece;

import java.util.HashSet;
import java.util.Set;

import core.BoardUtils;
import core.Color;
import core.Coordinates;
import core.CoordinatesShift;
import core.PieceType;
import model.Board;

/**
 * Base class for all pieces.
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
    
    public abstract PieceType getPieceType();
    
    public PieceType getBaseType() {
        return getPieceType().getBaseType();
    }

    public Color getColor() { return color; }
    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    
    /**
     * To be implemented by subclasses to define specific movement vectors.
     */
    protected abstract Set<CoordinatesShift> getPieceMoves();

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
    
    public boolean canPromote(Coordinates target) {
        return getPieceType().canPromote()
            && BoardUtils.isInPromotionZone(target, getColor());
    }
    
    /**
     * Some pieces must be forcibly promoted when 
     * no more moves are available on the board (for example, a pawn reaches the last rank).
     */
    public boolean mustPromote(Coordinates target) {
        return false;
    }
}
