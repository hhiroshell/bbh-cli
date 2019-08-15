package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "logout")
public class Logout implements Callable<Void> {

    @Override
    public Void call() {
        Config config = null;
        try {
            config = Config.loadCurrentConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (config == null) {
            System.out.println("Not logged in to.");
            return null;
        }
        Config.flashCurrentConfig();
        System.out.println("Login credentials are removed.");
        return null;
    }

}