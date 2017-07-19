// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.provider.jackson;

import com.yahoo.fili_config_utils.config.provider.descriptor.DimensionDescriptor;
import com.yahoo.bard.webservice.data.dimension.MapStoreManager;
import com.yahoo.bard.webservice.data.dimension.impl.NoOpSearchProviderManager;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Jacksonized dimension configuration.
 *
 * FIXME: Some means to override keyvalue store and search provider.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JacksonDimensionConfig extends DimensionDescriptor {

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
     * Construct a new dimension configuration object.
     *
     * @param apiName  The API name
     * @param longName  The long name
     * @param category  The category
     * @param physicalName  The physical name
     * @param description  The description
     * @param fields  The dimension fields
     * @param defaultFields  The default dimension fields
     * @param aggregatable  Whether this dimension can be aggregated
     */
    public JacksonDimensionConfig(
            @JsonProperty("name") final String apiName,
            @JsonProperty("long_name") final String longName,
            @JsonProperty("category") final String category,
            @JsonProperty("physical_name") final String physicalName,
            @JsonProperty("description") final String description,
            @JsonProperty("fields") final String[] fields,
            @JsonProperty("default_fields") final String[] defaultFields,
            @JsonProperty("aggregatable") final Boolean aggregatable
    ) {
        super(
                apiName,
                coalesceName(apiName, longName),
                coalesceName("default", category),
                coalesceName(apiName, physicalName),
                coalesceName(apiName, description),
                fields == null ? Collections.emptySet() : new HashSet<>(Arrays.asList(fields)),
                defaultFields == null ? Collections.emptySet() : new HashSet<>(Arrays.asList(defaultFields)),
                aggregatable == null ? false : aggregatable,
                MapStoreManager.getInstance(apiName),
                NoOpSearchProviderManager.getInstance(apiName)
        );
    }
}
