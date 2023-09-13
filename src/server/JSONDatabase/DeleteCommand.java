package server.JSONDatabase;

public class DeleteCommand implements Command{
    private Database db;
    private int index;

    public DeleteCommand(Database db, int index) {
        this.db = db;
        this.index = index;
    }

    @Override
    public String execute() {
        return db.delete(index);
    }
}
