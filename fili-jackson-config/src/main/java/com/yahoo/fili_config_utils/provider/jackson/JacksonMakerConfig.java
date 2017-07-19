// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.provider.jackson;

import com.yahoo.fili_config_utils.config.provider.descriptor.MakerDescriptor;
import com.yahoo.fili_config_utils.provider.jackson.serde.YamlArgDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;

/**
 * Jacksonized metric maker configuration.
 */
public class JacksonMakerConfig extends MakerDescriptor {
    /**
     * The configuration for a metric maker.
     *
     * @param name  The maker name to register
     * @param cls  The class name
     * @param args  The maker constructor arguments
     */
    public JacksonMakerConfig(
            @NotNull @JsonProperty("name") String name,
            @NotNull @JsonProperty("class") String cls,
            @NotNull @JsonProperty("args") @JsonDeserialize(using = YamlArgDeserializer.class) Object[] args) {
        super(name, cls, args);
    }
}
