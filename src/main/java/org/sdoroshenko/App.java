package org.sdoroshenko;

import org.sdoroshenko.command.CheckSum;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class App {
    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String[] args) {
        validateArgs(args);
        Callable<Integer> command = getCommand(args[0]);

        if (command != null) {
            String[] commandArgs = getCommandArgs(args);
            int exitCode = new CommandLine(command).execute(commandArgs);
            System.exit(exitCode);
        }
    }

    private static void validateArgs(String[] args) {
        if (args.length < 2) {
            System.out.println("Minimum arguments length is 2");
            System.exit(1);
        }
    }

    private static Callable<Integer> getCommand(String commandName) {
        switch (commandName) {
            case "check": return new CheckSum();
            default: {
                System.out.println(String.format("Command %s not found", commandName));
                return null;
            }
        }
    }

    private static String[] getCommandArgs(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length - 1);
    }
}
