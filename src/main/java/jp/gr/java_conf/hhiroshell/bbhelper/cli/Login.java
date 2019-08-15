package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveContext;
import jp.gr.java_conf.hhiroshell.beehive4j.exception.Beehive4jException;
import picocli.CommandLine;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "login")
public class Login implements Callable<Void> {

    @CommandLine.Option(names = {"-h", "--host"}, paramLabel = "HOST",
            description = "Beehive server URL (e.g. https://example.beehive.com/)")
    private URL host;

    @CommandLine.Option(names = {"-u", "--user"}, paramLabel = "USER", description = "Beehive account user name")
    private String user;

    @CommandLine.Option(names = {"-p", "--password"}, paramLabel = "PASSWORD", description = "Beehive account password",
            interactive = true)
    private String password;

    @Override
    public Void call() {
        try {
            BeehiveContext context = BeehiveContext.getBeehiveContext(host, user, password);
            if (context != null) {
                //TODO message
                System.out.println("Login succeeded :" + user + "@" + host);
            }
        } catch (Beehive4jException e) {
            // TODO logging
            System.out.println("Failed to Login: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            new Config().setHost(this.host).setUser(this.user).setPassword(this.password).persist();
        } catch (IOException e) {
            // TODO logging
            e.printStackTrace();
        }
        return null;
    }

}