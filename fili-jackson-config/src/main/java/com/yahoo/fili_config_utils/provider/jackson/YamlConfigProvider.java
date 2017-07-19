// Copyright 2016 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.provider.jackson;

import com.yahoo.bard.webservice.config.SystemConfig;
import com.yahoo.fili_config_utils.config.metric.parser.AntlrLogicalMetricBuilder;
import com.yahoo.fili_config_utils.config.metric.parser.FiliLogicalMetricParser;
import com.yahoo.fili_config_utils.config.provider.ConfigProvider;
import com.yahoo.fili_config_utils.config.provider.ConfigurationError;
import com.yahoo.fili_config_utils.config.provider.descriptor.DimensionDescriptor;
import com.yahoo.fili_config_utils.config.provider.descriptor.DimensionFieldDescriptor;
import com.yahoo.fili_config_utils.config.provider.LogicalMetricBuilder;
import com.yahoo.fili_config_utils.config.provider.descriptor.LogicalTableDescriptor;
import com.yahoo.fili_config_utils.config.provider.MakerBuilder;
import com.yahoo.fili_config_utils.config.provider.descriptor.MakerDescriptor;
import com.yahoo.fili_config_utils.config.provider.descriptor.MetricDescriptor;
import com.yahoo.fili_config_utils.config.provider.descriptor.PhysicalTableDescriptor;
import com.yahoo.bard.webservice.data.dimension.DimensionDictionary;
import com.yahoo.bard.webservice.data.metric.MetricDictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * YAML-based configuration.
 */
public class YamlConfigProvider implements ConfigProvider {
    private static final Logger LOG = LoggerFactory.getLogger(YamlConfigProvider.class);
    public static final String CONF_YAML_PATH = "config_binder_yaml_path";
    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    protected final List<PhysicalTableDescriptor> physicalTables;
    protected final List<MakerDescriptor> makers;
    protected final List<LogicalTableDescriptor> logicalTables;
    protected final List<DimensionDescriptor> dimensions;
    protected final List<MetricDescriptor> metrics;
    protected final List<DimensionFieldDescriptor> dimensionFields;

    /**
     * Instantiate the yaml-based configuration.
     *
     * @param physicalTables the physical table configuration
     * @param logicalTables the logical table configuration
     * @param dimensions the dimension configuration
     * @param dimensionFields the dimension field configuration
     * @param metrics the metric configuration
     * @param makers the metric makers
     */
    @JsonCreator
    public YamlConfigProvider(
            @NotNull @JsonProperty("physical_tables") @JsonDeserialize(contentAs = JacksonPhysicalTableConfig.class)
                    List<PhysicalTableDescriptor> physicalTables,
            @NotNull @JsonProperty("logical_tables") @JsonDeserialize(contentAs = JacksonLogicalTableConfig.class)
                    List<LogicalTableDescriptor> logicalTables,
            @NotNull @JsonProperty("dimensions") @JsonDeserialize(contentAs = JacksonDimensionConfig.class)
                     List<DimensionDescriptor> dimensions,
            @NotNull @JsonProperty("dimension_fields") @JsonDeserialize(contentAs = JacksonDimensionFieldConfig.class)
                    List<DimensionFieldDescriptor> dimensionFields,
            @NotNull @JsonProperty("metrics") @JsonDeserialize(contentAs = JacksonMetricConfig.class)
                    List<MetricDescriptor> metrics,
            @JsonProperty("makers") @JsonDeserialize(contentAs = JacksonMakerConfig.class)
                    List<MakerDescriptor> makers

    ) {
        this.physicalTables = physicalTables;
        this.logicalTables = logicalTables;
        this.dimensions = dimensions;
        this.metrics = metrics;
        if (makers == null) {
            LOG.info("No custom metric makers found.");
            makers = new LinkedList<>();
        }
        this.makers = makers;
        this.dimensionFields = dimensionFields;
    }

    @Override
    public List<PhysicalTableDescriptor> getPhysicalTableConfig() {
        return physicalTables;
    }

    @Override
    public List<LogicalTableDescriptor> getLogicalTableConfig() {
        return logicalTables;
    }

    @Override
    public List<DimensionDescriptor> getDimensionConfig() {
        return dimensions;
    }

    @Override
    public List<DimensionFieldDescriptor> getDimensionFieldConfig() {
        // FIXME
        return dimensionFields;
    }

    @Override
    public List<MetricDescriptor> getMetricConfig() {
        return metrics;
    }

    @Override
    public LogicalMetricBuilder getLogicalMetricBuilder(MetricDictionary metricDict, MakerBuilder makerBuilder, DimensionDictionary dimensionDict) {
        return new AntlrLogicalMetricBuilder(dimensionDict, makerBuilder, metricDict);
    }

    @Override
    public List<MakerDescriptor> getCustomMakerConfig() {
        return makers;
    }

    /**
     * Build self from yaml file.
     *
     * @param systemConfig the system configuration
     * @return a ConfigProvider
     *
     * @see com.yahoo.fili_config_utils.config.provider.ConfigBinderFactory
     */
    public static ConfigProvider build(SystemConfig systemConfig) {
        String path = systemConfig.getStringProperty(
                systemConfig.getPackageVariableName(CONF_YAML_PATH)
        );

        if (path == null) {
            throw new ConfigurationError("Could not read path variable: " + CONF_YAML_PATH);
        }

        File f = new File(path);
        if (!f.exists() || !f.canRead()) {
            throw new ConfigurationError("Could not read path: " + path + ". Please ensure it exists and is readable.");
        }

        try {
            LOG.info("Loading YAML configuration from path: {}", path);
            return build(f);
        } catch (Exception e) {
            throw new ConfigurationError("Could not parse path: " + path, e);
        }
    }

    /**
     * Build self from yaml file.
     *
     * @param file the YAML file to read
     * @return a ConfigProvider
     * @throws IOException when the file cannot be parsed
     * @see com.yahoo.fili_config_utils.config.provider.ConfigBinderFactory
     */
    public static ConfigProvider build(File file) throws IOException {
        return MAPPER.readValue(file, YamlConfigProvider.class);
    }
}
