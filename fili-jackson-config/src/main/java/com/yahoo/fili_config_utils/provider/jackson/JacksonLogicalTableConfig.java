// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.provider.jackson;

import com.yahoo.fili_config_utils.config.provider.descriptor.LogicalTableDescriptor;
import com.yahoo.fili_config_utils.provider.jackson.serde.TimeGrainDeserializer;
import com.yahoo.bard.webservice.data.time.TimeGrain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.HashSet;

import javax.validation.constraints.NotNull;

/**
 * Jacksonized logical table configuration.
 */
public class JacksonLogicalTableConfig extends LogicalTableDescriptor {

    /**
     * Construct a new logical table configuration object.
     *
     * @param name  Logical table name
     * @param timeGrains  Logical table time grains
     * @param physicalTables  Physical tables backing this logical table
     * @param metrics  Metrics included on this logical table
     */
    @JsonCreator
    public JacksonLogicalTableConfig(
            @NotNull @JsonProperty("name") final String name,
            @NotNull @JsonProperty("granularities") @JsonDeserialize(contentUsing = TimeGrainDeserializer.class) final TimeGrain[] timeGrains,
            @NotNull @JsonProperty("physical_tables") final String[] physicalTables,
            @NotNull @JsonProperty("metrics") final String[] metrics
    ) {
        super(name, new HashSet<>(Arrays.asList(timeGrains)), new HashSet<>(Arrays.asList(physicalTables)), new HashSet<>(Arrays.asList(metrics)));
    }
}
