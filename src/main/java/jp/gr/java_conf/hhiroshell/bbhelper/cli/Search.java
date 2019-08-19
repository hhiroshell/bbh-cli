package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import com.fasterxml.jackson.databind.JsonNode;
import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveApiDefinitions;
import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveContext;
import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveResponse;
import jp.gr.java_conf.hhiroshell.beehive4j.InvtListByRangeInvoker;
import jp.gr.java_conf.hhiroshell.beehive4j.exception.BeehiveApiFaultException;
import jp.gr.java_conf.hhiroshell.beehive4j.model.BeeId;
import jp.gr.java_conf.hhiroshell.beehive4j.model.CalendarRange;
import org.springframework.http.ResponseEntity;
import picocli.CommandLine;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@CommandLine.Command(name = "search")
public class Search extends ContextBasedCommand {

    @CommandLine.Option(
            names = {"-s", "--start-time"}, paramLabel = "START-TIME", description = "Start time for your meeting.")
    private ZonedDateTime start;

    @CommandLine.Option(
            names = {"-d", "--duration"}, paramLabel = "DURATION", description = "Duration for you meeting (min).")
    private int duration;

    @Override
    void doIt(BeehiveContext context) throws Exception {
        List<Exception> exceptions = new ArrayList<>();
        Set<Resource> vacants = new TreeSet<>();
        Resources.getUnrestrictedConferenceResources().stream().parallel().forEach(r -> {
            try {
                InvtListByRangeInvoker invoker = context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_LIST_BY_RANGE);
                CalendarRange range = new CalendarRange.Builder()
                        .beeId(new BeeId.Builder().id(r.getBeeid()).build())
                        .start(start)
                        .end(start.plusMinutes(duration))
                        .build();
                invoker.setRequestPayload(range);
                ResponseEntity<BeehiveResponse> response = invoker.invoke();
                // beehive4jがExceptionを上げないときは、正常な結果が返ったときとみなしてよい
                BeehiveResponse body = response.getBody();
                String invitationId = null;
                if (response != null && body != null) {
                    Iterable<JsonNode> elements = body.getJson().get("elements");
                    if (elements != null) {
                        for (JsonNode element : elements) {
                            invitationId = getNodeAsText(element, "collabId", "id");
                        }
                    }
                }
                if (invitationId == null || invitationId.isEmpty()) {
                    vacants.add(r);
                }
            } catch (BeehiveApiFaultException e) {
                exceptions.add(e);
            }
        });
        if (exceptions.size() > 0) {
            throw new Exception(exceptions.toString());
        }
        if (vacants.size() == 0) {
            System.out.println("No Free Resource Found.");
        }
        vacants.forEach(r -> {System.out.println(r.getName());});
    }

    private String getNodeAsText(JsonNode node, String... names) {
        if (node == null) {
            throw new NullPointerException();
        }
        if (names.length == 0) {
            return node.asText();
        }
        for (String name : names) {
            if ((node = node.get(name)) == null) {
                return null;
            }
        }
        return node.asText();
    }

}
