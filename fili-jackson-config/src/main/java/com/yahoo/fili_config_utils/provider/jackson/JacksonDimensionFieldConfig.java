// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.provider.jackson;

import com.yahoo.fili_config_utils.config.provider.descriptor.DimensionFieldDescriptor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jacksonized dimension field configuration.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JacksonDimensionFieldConfig extends DimensionFieldDescriptor {

    /**
     * Construct a new dimension field configuration object.
     *
     * @param name  the field name
     * @param description  The field description
     * @param queryIncludedByDefault  Whether this field is included by default in query results
     * @param dimensionIncludedByDefault  Whether this field is included by default on all dimensions
     */
    public JacksonDimensionFieldConfig(
            @JsonProperty("name") final String name,
            @JsonProperty("description") final String description,
            @JsonProperty("query_included_by_default") final Boolean queryIncludedByDefault,
            @JsonProperty("dimension_included_by_default") final Boolean dimensionIncludedByDefault
    ) {
        super(
                name,
                description == null ? name : description,
                queryIncludedByDefault == null ? false : queryIncludedByDefault,
                dimensionIncludedByDefault == null ? false : dimensionIncludedByDefault
        );
    }
}
