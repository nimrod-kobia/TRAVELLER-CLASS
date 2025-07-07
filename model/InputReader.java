package model;

public interface InputReader {
    String readLine(String prompt);
    int readInt(String prompt);
    double readDouble(String prompt);
    void close();
}
