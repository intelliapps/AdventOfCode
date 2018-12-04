package advent;

import java.io.FileWriter;

public class Log
{
    private FileWriter fileWriter;
    private String prefix;

    public Log(String prefix, String filename) throws Exception
    {
        this.prefix = prefix;
        this.fileWriter = new FileWriter(filename);
    }

    public void debug(String message) throws Exception
    {
        fileWriter.write(prefix + " - " + message + '\n');
    }

    public void flush() throws Exception
    {
        fileWriter.flush();
        fileWriter.close();;
    }
}
