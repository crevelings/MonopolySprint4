package org.monopoly.Exceptions;

/**
 * Property is already bought or is unavailable
 * @author walshj05
 */
public class NoSuchPropertyException extends Exception {
    public NoSuchPropertyException(String message) {
        super(message);
    }
}
