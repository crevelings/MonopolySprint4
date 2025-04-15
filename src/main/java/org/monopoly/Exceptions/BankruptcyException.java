package org.monopoly.Exceptions;

/**
 * Player goes broke.
 * @author walshj05
 */
public class BankruptcyException extends Exception {
    public BankruptcyException(String message) {
        super(message);
    }
}
