package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveApiDefinitions;
import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveResponse;
import jp.gr.java_conf.hhiroshell.beehive4j.MyWorkspaceInvoker;
import jp.gr.java_conf.hhiroshell.beehive4j.exception.Beehive4jException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import picocli.CommandLine;

@CommandLine.Command(name = "search")
public class Search extends ContextBasedCommand {

    @Override
    void doIt() {
        MyWorkspaceInvoker invoker = context.getInvoker(BeehiveApiDefinitions.TYPEDEF_MY_WORKSPACE);
        try {
            // TODO This is dummy implementation.
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println("Success !!!");
            }
        } catch (Beehive4jException e) {
            e.printStackTrace();
        }
    }

}
