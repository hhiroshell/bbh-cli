package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "beeid"})
class Resource implements Comparable<Resource> {

    private final String name;

    private final String beeid;

    private final ResourceType type;

    private boolean restricted = false;

    private static final String RESOURCE_TYPE_VIDEO_SYMBOL = "-VIDEO-";
    private static final String RESOURCE_TYPE_TEAROOM_SYMBOL = "_Tearoom";
    private static final String RESOURCE_TYPE_BOARDROOM_SYMBOL = "_BoardRoom";
    private static final String RESOURCE_TYPE_LOUNGE_SYMBOL = "_Lounge";
    private static final String RESTRICTED_RESOURCE_SYMBOL = "_R";

    @JsonCreator
    Resource(@JsonProperty("name") final String name, @JsonProperty("beeid") final String beeid) {
        this.name = name;
        this.beeid = beeid;
        if (name.endsWith(RESTRICTED_RESOURCE_SYMBOL)) {
            this.restricted = true;
        }
        if (name.contains(RESOURCE_TYPE_VIDEO_SYMBOL)) {
            this.type = ResourceType.VIDEO;
        } else if (name.contains(RESOURCE_TYPE_TEAROOM_SYMBOL)) {
            this.type = ResourceType.TEAROOM;
        } else if (name.contains(RESOURCE_TYPE_BOARDROOM_SYMBOL)) {
            this.type = ResourceType.BOARDROOM;
        } else if (name.contains(RESOURCE_TYPE_LOUNGE_SYMBOL)) {
            this.type = ResourceType.LOUNGE;
        } else {
            this.type = ResourceType.CONFERENCE;
        }
    }

    public String getName() {
        return name;
    }

    public String getBeeid() {
        return beeid;
    }

    public ResourceType getType() {
        return type;
    }

    public boolean isRestricted() {
        return restricted;
    }

    @Override
    public int compareTo(Resource o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + name + '\'' +
                ", beeid='" + beeid + '\'' +
                ", type=" + type +
                ", restricted=" + restricted +
                '}';
    }

}