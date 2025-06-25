
public class ConsoleOutputWriter {
    public void write(String message) {
        System.out.println(message);
    }

    public void writeError(String errorMessage) {
        System.err.println(errorMessage);
    }

    public void writeWarning(String warningMessage) {
        System.out.println("Warning: " + warningMessage);
    }

    public void writeInfo(String infoMessage) {
        System.out.println("Info: " + infoMessage);
    }

}
