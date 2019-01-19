package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import jp.gr.java_conf.hhiroshell.beehive4j.*;
import jp.gr.java_conf.hhiroshell.beehive4j.exception.*;
import picocli.*;

import java.net.*;
import java.util.concurrent.*;

@CommandLine.Command(name = "login")
public class Login implements Callable<Void> {

    @CommandLine.Option(names = {"-h", "--host"}, paramLabel = "HOST", description = "host.")
    private URL host;

    @CommandLine.Option(names = {"-u", "--user"}, paramLabel = "USER", description = "user.")
    private String user;

    @CommandLine.Option(names = {"-p", "--password"}, paramLabel = "PASSWORD", description = "password.")
    private String password;

    @Override
    public Void call() throws Exception {
        try {
            BeehiveContext context = BeehiveContext.getBeehiveContext(host, user, password);
        } catch (Beehive4jException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
