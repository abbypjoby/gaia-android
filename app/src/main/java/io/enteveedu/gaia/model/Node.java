package io.enteveedu.gaia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by z002r13 on 12/18/17.
 */

@Data
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
    private Integer isOn;

}
