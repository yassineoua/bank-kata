package com.yassineoua.bank.application.boot;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ConsoleManager {
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private final PrintStream printStream;
    private final Scanner scanner;

    public ConsoleManager() {
        this.printStream = System.out;
        this.scanner = new Scanner(System.in);
    }

    public void print(String str) {
        this.printStream.println(str);
    }

    public void format(String str, Object... args) {
        this.printStream.format(str, args);
    }

    public String readString(String label) {
        printLabel(label);
        return scanner.next();
    }

    public Integer readInt(String label) {
        printLabel(label);
        return scanner.nextInt();
    }

    public Long readLong(String label) {
        printLabel(label);
        return scanner.nextLong();
    }

    public BigDecimal redAmount(String label) {
        printLabel(label);
        return scanner.nextBigDecimal();
    }

    private void printLabel(String label) {
        this.printStream.print(label);
    }

    public void breakLines(int n) {
        IntStream.range(0, n).forEach(value -> printStream.println());
    }

    public void printError(String message) {
        printStream.println(String.format(RED + "[ERROR] : %s" + ANSI_RESET, message));
    }

    public void printSuccess(String message) {
        printStream.println(String.format(GREEN + "[SUCCESS] : %s" + ANSI_RESET, message));
    }
}
