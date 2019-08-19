package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Resources {

    static {
        initialize();
    }

    private static List<Resource> resources;

    private static void initialize() {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Resource.class);
        try (InputStream in = Resources.class.getClassLoader().getResourceAsStream("resource-list.csv")) {
            MappingIterator<Resource> it = mapper.readerFor(Resource.class).with(schema).readValues(in);
            resources = Collections.unmodifiableList(it.readAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<Resource> getAllResources() {
        return resources;
    }

    static List<Resource> getUnrestrictedConferenceResources() {
        List<Resource> resultSet = new ArrayList<>();
        resources.forEach(resource -> {
            if (ResourceType.CONFERENCE.equals(resource.getType()) && !resource.isRestricted()) {
                resultSet.add(resource);
            }
        });
        return Collections.unmodifiableList(resultSet);
    }

}
