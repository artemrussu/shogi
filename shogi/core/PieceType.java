package core;

public enum PieceType {
    PAWN, LANCE, KNIGHT, SILVER, GOLD, BISHOP, ROOK, KING,
    TOKIN, PROMOTED_LANCE, PROMOTED_KNIGHT, PROMOTED_SILVER, HORSE, DRAGON;

    public PieceType getBaseType() {
        if (this == TOKIN)           return PAWN;
        if (this == PROMOTED_LANCE)  return LANCE;
        if (this == PROMOTED_KNIGHT) return KNIGHT;
        if (this == PROMOTED_SILVER) return SILVER;
        if (this == HORSE)           return BISHOP;
        if (this == DRAGON)          return ROOK;
        return this;
    }
    
    public PieceType getPromotedType() {
        if (this == PAWN)   return TOKIN;
        if (this == LANCE)  return PROMOTED_LANCE;
        if (this == KNIGHT) return PROMOTED_KNIGHT;
        if (this == SILVER) return PROMOTED_SILVER;
        if (this == BISHOP) return HORSE;
        if (this == ROOK)   return DRAGON;
        throw new RuntimeException(this + " cannot promote");
    }

    public boolean canPromote() {
        return this == PAWN   || this == LANCE  || this == KNIGHT ||
               this == SILVER || this == BISHOP || this == ROOK;
    }
}