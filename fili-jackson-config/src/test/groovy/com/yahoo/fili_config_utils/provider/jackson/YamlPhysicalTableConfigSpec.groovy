// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.

package com.yahoo.fili_config_utils.provider.jackson

import com.yahoo.bard.webservice.data.time.DefaultTimeGrain
import spock.lang.Specification

public class YamlPhysicalTableConfigSpec extends Specification {

    def "Basic constructor works"() {
        setup:
        def t =  new JacksonPhysicalTableConfig("table", DefaultTimeGrain.HOUR, ["dim1"] as String[], ["m1"] as String[])

        expect:
        t.getMetrics().toSet() == ["m1"] as Set
    }

// fixme ; cleanup this test
//     def "TimeGrain required"() {
//         when:
//         new JacksonPhysicalTableConfig("table", null, ["dim1"] as String[], ["m1"] as String[])
//         then:
//         ConfigurationError ex = thrown()
//         ex.message =~ /ZonelessTimeGrain required.*/
//     }
//
//     def "Dimensions required"() {
//         when:
//         new JacksonPhysicalTableConfig("table", DefaultTimeGrain.HOUR, null, ["m1"] as String[])
//         then:
//         ConfigurationError ex = thrown()
//         ex.message =~ /.*with dimensions.*/
//     }
//
//     def "Metrics required"() {
//         when:
//         new JacksonPhysicalTableConfig("table", DefaultTimeGrain.HOUR, ["dim1"] as String[], null)
//         then:
//         ConfigurationError ex = thrown()
//         ex.message =~ /.*with metrics.*/
//     }

}
