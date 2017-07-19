// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.provider.jackson;

import com.yahoo.fili_config_utils.config.provider.descriptor.MetricDescriptor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jacksonized metric configuration.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JacksonMetricConfig extends MetricDescriptor {

    /**
     * Return otherName if not null, else name.
     *
     * @param name  Default name
     * @param otherName  Override name
     * @return otherName if not null, else name
     */
    public static String coalesceName(String name, String otherName) {
        return otherName != null ? otherName : name;
    }

    /**
     * Construct a new metric configuration object.
     *
     * @param name  The metric name
     * @param longName  The long metric name
     * @param category  The metric category
     * @param description  The metric description
     * @param definition  The metric definition
     * @param isExcluded  True to exclude the metric from the final metric dictionary
     */
    public JacksonMetricConfig(
            @JsonProperty("name") final String name,
            @JsonProperty("long_name") final String longName,
            @JsonProperty("category") final String category,
            @JsonProperty("description") final String description,
            @JsonProperty("definition") final String definition,
            @JsonProperty("is_excluded") final boolean isExcluded
    ) {
        super(
                name,
                coalesceName(name, longName),
                coalesceName("default", category),
                coalesceName(name, description),
                definition,
                isExcluded,
                null // FIXME validGrains
        );
    }
}
