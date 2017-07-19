// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.

package com.yahoo.fili_config_utils.provider.jackson


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import spock.lang.Specification

class YamlDimensionConfigSpec extends Specification {

    static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    def "Can deserialize from yaml correctly"() {
        setup:
        String conf = """
                     |physical_name: physicalName
                     |long_name: Longer Name
                     |category: some_category
                     |description: some description
                     |fields: [f1, f2]
                     |default_fields: [f1]
                     |aggregatable: false
                     |""".stripMargin()
        def dim = mapper.readValue(conf, JacksonDimensionConfig.class)

        expect:
        dim.isAggregatable() == false
        dim.getPhysicalName() == "physicalName"
        dim.getLongName() == "Longer Name"
        dim.getCategory() == "some_category"
        dim.getDescription() == "some description"
        dim.getDefaultFields() == (Set) ["f1"]
        dim.getFields() == (Set) ["f1", "f2"]
    }
}
