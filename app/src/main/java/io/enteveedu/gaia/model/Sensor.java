package io.enteveedu.gaia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;

/**
 * Created by z002r13 on 12/22/17.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class Sensor {

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "created_at")
    private Timestamp createdTimestamp;

    @JsonProperty(value = "node_id")
    private Integer nodeId;

    @JsonProperty(value = "measurements")
    private String measurements;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", createdTimestamp=" + createdTimestamp +
                ", nodeId=" + nodeId +
                ", measurements='" + measurements + '\'' +
                '}';
    }
}
