package model;

public interface OutputWriter {
    void write(String message);
    void writeError(String errorMessage);
    void writeWarning(String warningMessage);
    void writeInfo(String infoMessage);
}
