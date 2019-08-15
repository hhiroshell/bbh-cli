package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(description = "Beehive Booking Helper CLI Edition.", name = "bbh", mixinStandardHelpOptions = true,
        version = "0.1")
public class Main {

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new Main());
        commandLine.addSubcommand("login", new Login())
                    .addSubcommand("logout", new Logout())
                    .addSubcommand("search", new Search());
        List<CommandLine> parsed = commandLine.parse(args);
        handleParseResult(parsed);
    }

    private static void handleParseResult(List<CommandLine> parsed) {
        // TODO Print usage if any subcommands are not specified.
        Callable<Void> command = parsed.get(1).getCommand();
        try {
            command.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
