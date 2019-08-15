package jp.gr.java_conf.hhiroshell.bbhelper.cli;


import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveContext;
import jp.gr.java_conf.hhiroshell.beehive4j.exception.BeehiveApiFaultException;

import java.util.concurrent.Callable;

abstract class ContextBasedCommand implements Callable<Void> {

    static protected BeehiveContext context;

    @Override
    public Void call() throws Exception {
        if (context == null) {
            Config config = Config.loadCurrentConfig();
            if (config == null) {
                // TODO help
                System.out.println("You need to be loggeed in.");
                return null;
            }
            try {
                context = BeehiveContext.getBeehiveContext(config.getHost(), config.getUser(), config.getPassword());
            } catch (BeehiveApiFaultException e) {
                e.printStackTrace();
            }
        }
        doIt();
        return null;
    }

    abstract void doIt();

}