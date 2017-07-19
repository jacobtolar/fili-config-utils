// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.provider.jackson;

import com.yahoo.fili_config_utils.config.provider.descriptor.PhysicalTableDescriptor;
import com.yahoo.fili_config_utils.provider.jackson.serde.GranularityDeserializer;
import com.yahoo.bard.webservice.druid.model.query.Granularity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Jacksonized physical table configuration.
 *
 * FIXME: How do we handle non-default timegrains?
 */
public class JacksonPhysicalTableConfig extends PhysicalTableDescriptor {

    /**
     * Construct a new PhysicalTableDescriptor object.
     *
     * @param name  the table name
     * @param granularity  the table grain
     * @param dimensions  the table dimensions
     * @param metrics  the table metrics
     */
    @JsonCreator
    public JacksonPhysicalTableConfig(
            @JsonProperty("name") final String name,
            @JsonProperty("granularity") @JsonDeserialize(using = GranularityDeserializer.class) Granularity granularity,
            @JsonProperty("dimensions") final String[] dimensions,
            @JsonProperty("metrics") final String[] metrics
    ) {
        super(name, granularity, new HashSet<>(Arrays.asList(dimensions)), new HashSet<>(Arrays.asList(metrics)));

        // Previous version had this:
        //
        /*
        if (!(grain instanceof ZonelessTimeGrain)) {
            throw new ConfigurationError("ZonelessTimeGrain required; found " + grain);
        }
        if (dimensions == null || dimensions.length == 0) {
            throw new ConfigurationError("Physical table must be configured with dimensions; found null.");
        }

        if (metrics == null || metrics.length == 0) {
            throw new ConfigurationError("Physical table must be configured with metrics; found null.");
        }
         */
    }
}
