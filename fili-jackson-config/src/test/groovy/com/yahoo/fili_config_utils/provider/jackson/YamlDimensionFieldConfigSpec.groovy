// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.

package com.yahoo.fili_config_utils.provider.jackson

import spock.lang.Specification

public class YamlDimensionFieldConfigSpec extends Specification {

    def "Defaults set correctly by name"() {
        setup:
        def dim =  new JacksonDimensionFieldConfig("some name", null, null, null)

        expect:
        !dim.dimensionIncludedByDefault
        !dim.queryIncludedByDefault
        dim.getDescription() == "some name"
        dim.getName() == "some name"
    }
}
