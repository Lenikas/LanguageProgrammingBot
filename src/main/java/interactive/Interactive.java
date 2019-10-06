package interactive;

public interface Interactive
{
    String readCommand();

    void send(final String message);
}
