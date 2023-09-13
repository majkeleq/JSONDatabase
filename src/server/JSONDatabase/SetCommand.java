package server.JSONDatabase;

public class SetCommand implements Command{
    private Database db;
    private int index;
    private String message;

    public SetCommand(Database db, int index, String message) {
        this.db = db;
        this.index = index;
        this.message = message;
    }

    @Override
    public String execute() {
        return db.set(index, message);
    }
}
