package domain.booking.command;

/**
 * Command pattern contract encapsulating a reversible booking action that can
 * be executed by a {@link CommandInvoker}.
 */
public interface Command {

    /**
     * Executes the encapsulated action.
     */
    void execute();
}
