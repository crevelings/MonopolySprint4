package org.monopoly.Exceptions;

/**
 * Insufficient Funds Exception used to handle when a player does not have enough money
 * @author walshj05
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
