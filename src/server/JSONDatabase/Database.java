package server.JSONDatabase;

import javax.xml.crypto.Data;
import java.util.Arrays;

public class Database {
    String[] db = new String[100];

    public Database() {
        Arrays.fill(db, "");
    }

    public String get(int i) {
        try {
            if (db[i - 1].isEmpty()) throw new Exception("ERROR");
            return db[i -1];
        } catch (Exception e) {
            return "ERROR";
        }
    }
    public String set(int i, String content) {
        try {
            db[i - 1] = content;
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }
    public String delete(int i) {
        try {
            db[i - 1] = "";
            return "OK";
        } catch (Exception e) {
            return "ERROR";
        }
    }
}
