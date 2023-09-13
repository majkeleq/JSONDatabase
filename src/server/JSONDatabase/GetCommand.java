package server.JSONDatabase;

public class GetCommand implements Command {
    private Database db;
    private int index;

    public GetCommand(Database db, int index) {
        this.db = db;
        this.index = index;
    }

    @Override
    public String execute() {
        return db.get(index);
    }
}
