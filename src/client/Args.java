package client;
import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = "-t", description = "Operation type")
    public String operationType = "exit";
    @Parameter(names = "-k", description = "Key")
    public String key;
    @Parameter(names = "-v", description = "Value")
    public String value;
}
