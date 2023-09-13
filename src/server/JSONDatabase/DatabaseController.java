package server.JSONDatabase;

public class DatabaseController {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }
    public String executeCommand() {
        return command.execute();
    }
}
