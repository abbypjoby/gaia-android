package io.enteveedu.gaia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Created by z002r13 on 12/18/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "updated_at")
    private Timestamp updatedTimestamp;

    @JsonProperty(value = "node_id")
    private Integer nodeId;

    @JsonProperty(value = "node_type")
    private String nodeType;

    @JsonProperty(value = "is_on")
    private Boolean isOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Boolean getIsOn() {
        return isOn;
    }

    public void setOn(Boolean on) {
        isOn = on;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", updatedTimestamp=" + updatedTimestamp +
                ", nodeId=" + nodeId +
                ", nodeType='" + nodeType + '\'' +
                ", isOn=" + isOn +
                '}';
    }
}
