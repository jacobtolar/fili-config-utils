// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.

package com.yahoo.fili_config_utils.provider.jackson

import com.yahoo.bard.webservice.config.SystemConfig
import com.yahoo.bard.webservice.config.SystemConfigException
import com.yahoo.bard.webservice.config.SystemConfigProvider
import com.yahoo.bard.webservice.data.config.metric.makers.ThetaSketchSetOperationMaker
import com.yahoo.bard.webservice.data.time.DefaultTimeGrain
import com.yahoo.bard.webservice.druid.model.postaggregation.SketchSetOperationPostAggFunction
import com.yahoo.fili_config_utils.config.provider.ConfigurationError
import spock.lang.Specification

public class YamlConfigProviderSpec extends Specification {
    static final SystemConfig SYSTEM_CONFIG = SystemConfigProvider.getInstance()


    def "test missing setting"() {
        setup:
        SYSTEM_CONFIG.resetProperty(SYSTEM_CONFIG.getPackageVariableName(YamlConfigProvider.CONF_YAML_PATH), null)
        when:
        YamlConfigProvider.build(SYSTEM_CONFIG)
        then:
        SystemConfigException ex = thrown()
    }

    def "test setting that doesn't point to file"() {
        setup:
        SYSTEM_CONFIG.setProperty(SYSTEM_CONFIG.getPackageVariableName(YamlConfigProvider.CONF_YAML_PATH), "/hopefully/not/a/file")
        when:
        YamlConfigProvider.build(SYSTEM_CONFIG)
        then:
        ConfigurationError ex = thrown()
        ex.message =~ /Could not read path.*/
    }

    def "Test loading of config file"() {
        setup:
        String path = YamlConfigProviderSpec.class.getResource("/yaml/exampleConfiguration.yaml").getPath()
        SYSTEM_CONFIG.setProperty(SYSTEM_CONFIG.getPackageVariableName(YamlConfigProvider.CONF_YAML_PATH), path)

        when:
        def conf = YamlConfigProvider.build(SYSTEM_CONFIG)

        then:
        // Metric configs should exist
        conf.getMetricConfig().stream().anyMatch({a -> a.getName() == "impressions"})
        conf.getMetricConfig().stream().anyMatch({a -> a.getName() == "incremented_impressions"})

        // Dimension configs should exist with correct fields
        def tld = conf.getDimensionConfig()
                .stream()
                .filter({a -> a.getApiName() == "tld"})
                .findFirst()
                .get()

        def platform = conf.getDimensionConfig()
                .stream()
                .filter({a -> a.getApiName() == "platform"})
                .findFirst()
                .get()

        // This is now managed by the config binder factory
        // tld.getDefaultFields() == ["id"].toSet()
        // tld.getFields() == ["id", "description"].toSet()

        platform.getDefaultFields() == ["id"].toSet()
        platform.getFields().toSet() == ["id"].toSet()

        conf.getPhysicalTableConfig().size() == 2
        conf.getPhysicalTableConfig().get(0).getName() == "physical_table_1"
        conf.getPhysicalTableConfig().get(0).getMetrics() == ["impressions"] as Set
        conf.getPhysicalTableConfig().get(1).getMetrics() == ["impressions"] as Set

        conf.getLogicalTableConfig().size() == 1
        conf.getLogicalTableConfig().get(0).getName() == "logical_table_1"
        conf.getLogicalTableConfig().get(0).getMetrics() == ["impressions", "incremented_impressions"] as Set
        conf.getLogicalTableConfig().get(0).getPhysicalTables() == ["physical_table_1", "physical_table_2"] as Set
        conf.getLogicalTableConfig().get(0).getTimeGrains() == [DefaultTimeGrain.DAY, DefaultTimeGrain.HOUR, DefaultTimeGrain.MINUTE] as Set

        conf.getCustomMakerConfig().get(0).getName() == "sketch"
        conf.getCustomMakerConfig().get(0).getMakerClass() == ThetaSketchSetOperationMaker.class
        conf.getCustomMakerConfig().get(0).getArguments()[0] == SketchSetOperationPostAggFunction.NOT
    }
}
