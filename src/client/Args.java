package client;
import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = "-t", description = "Operation type")
    public String operationType = "exit";
    @Parameter(names = "-i", description = "Index")
    public int index;
    @Parameter(names = "-m", description = "Message")
    public String message;
}
