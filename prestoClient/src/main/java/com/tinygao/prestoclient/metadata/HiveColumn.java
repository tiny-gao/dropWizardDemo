package com.tinygao.prestoclient.metadata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.concurrent.Immutable;

/**
 * Created by gsd on 2016/11/20.
 */
@Immutable
public class HiveColumn extends Column {

    private final boolean isPartition;
    private final boolean isNullable;

    @JsonCreator
    public HiveColumn(@JsonProperty("name") String name,
                      @JsonProperty("type") String type,
                      @JsonProperty("isPartition") boolean isPartition,
                      @JsonProperty("isNullable") boolean isNullable) {
        this.isPartition = isPartition;
        this.isNullable = isNullable;
    }

    @JsonProperty
    public boolean isPartition() {
        return isPartition;
    }

    @JsonProperty
    public boolean isNullable() {
        return isNullable;
    }

    public static HiveColumn fromColumn(Column column, boolean isNullable, boolean isPartition) {
        return new HiveColumn(column.getName(), column.getType(),isPartition, isNullable);
    }
}