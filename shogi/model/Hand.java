package model;

import core.Color;
import core.PieceType;
import java.util.HashMap;

/**
 * Represents the set of captured pieces held in hand by each player;
 * In shogi, pieces captured from the opponent are kept in hand and can be dropped
 * back onto the board as one's own pieces.
 */
public class Hand {

    private HashMap<PieceType, Integer> sentePieces = new HashMap<>();
    private HashMap<PieceType, Integer> gotePieces  = new HashMap<>();
    
    public HashMap<PieceType, Integer> getHand(Color color) {
        if (color == Color.SENTE) return sentePieces;
        return gotePieces;
    }

    /**
     * Adds one piece of the specified type to the hand of the given player.
     */
    public void add(Color color, PieceType type) {
        HashMap<PieceType, Integer> hand = getHand(color);
        int current = hand.getOrDefault(type, 0);
        hand.put(type, current + 1);
    }

    /**
     * Removes one piece of the specified type from the hand of the given player.
     */
    public void remove(Color color, PieceType type) {
        HashMap<PieceType, Integer> hand = getHand(color);
        int current = hand.getOrDefault(type, 0);
        if (current > 0) {
            hand.put(type, current - 1);
        }
    }

    /**
     * Checks whether the given player has at least one piece of the specified type in hand.
     */
    public boolean has(Color color, PieceType type) {
        return getHand(color).getOrDefault(type, 0) > 0;
    }
}
