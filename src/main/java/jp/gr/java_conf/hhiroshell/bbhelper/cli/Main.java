package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import picocli.CommandLine;

import java.util.*;
import java.util.concurrent.*;

@CommandLine.Command(description = "Beehive Booking Helper CLI Edition.",
        name = "bbh", mixinStandardHelpOptions = true, version = "0.1")
class Main {

    public static void main(String[] args) throws Exception {
        // Set up the parser
        CommandLine commandLine = new CommandLine(new Main());

        // add subcommands programmatically (not necessary if the parent command
        // declaratively registers the subcommands via annotation)
        commandLine.addSubcommand("login",   new Login());

        // Invoke the parse method to parse the arguments
        List<CommandLine> parsed = commandLine.parse(args);
        handleParseResult(parsed);
    }

    private static void handleParseResult(List<CommandLine> parsed) {
        assert parsed.size() == 2 : "1 command and 1 subcommand found";
        Callable<Void> command = parsed.get(1).getCommand();
        try {
            command.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

