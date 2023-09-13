package server.JSONDatabase;

public class ExitCommand implements Command{

    @Override
    public String execute() {
        return "OK";
    }
}
