package it.polimi.ingsw.sagrada.game.rules;


/**
 * The Enum ErrorType.
 */
public enum ErrorType {
    
    /** The errno same ortogonal color value. */
    ERRNO_SAME_ORTOGONAL_COLOR_VALUE,
    
    /** The errno cell rule not validated. */
    ERRNO_CELL_RULE_NOT_VALIDATED,
    
    /** The no error. */
    NO_ERROR,
    
    /** The null data. */
    NULL_DATA,
    
    /** The max dice value exceeded. */
    MAX_DICE_VALUE_EXCEEDED,
    
    /** The matrix error. */
    MATRIX_ERROR,
    
    /** The error. */
    ERROR,

    /** Player already placed a dice*/
    ALREADY_PLAYED;
}
