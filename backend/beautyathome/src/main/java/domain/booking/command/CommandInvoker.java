package domain.booking.command;

/**
 * Invoker that stores the current {@link Command} and triggers its execution
 * when requested.
 */
public class CommandInvoker {

    private Command command;

    /**
     * Registers the command that should be executed next.
     *
     * @param command command instance to execute
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Executes the previously configured command.
     */
    public void executeCommand() {
        if (command == null) {
            throw new IllegalStateException("No command configured");
        }
        command.execute();
    }
}
