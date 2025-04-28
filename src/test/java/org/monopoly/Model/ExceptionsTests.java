package org.monopoly.Model;

import org.junit.jupiter.api.Test;
import org.monopoly.Exceptions.InsufficientFundsException;
import org.monopoly.Exceptions.NoSuchPropertyException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the Exceptions in the Monopoly game.
 * @author shifmans
 */
public class ExceptionsTests {

    /**
     * Developed by: shifmans
     */
    @Test
    void testInsufficientFundsExceptionThrown() {
        assertThrows(InsufficientFundsException.class, () -> {
            throw new InsufficientFundsException("Invalid argument");
        });
    }

    /**
     * Developed by: shifmans
     */
    @Test
    void testNoSuchPropertyExceptionThrown() {
        assertThrows(NoSuchPropertyException.class, () -> {
            throw new NoSuchPropertyException("Invalid argument");
        });
    }
}
