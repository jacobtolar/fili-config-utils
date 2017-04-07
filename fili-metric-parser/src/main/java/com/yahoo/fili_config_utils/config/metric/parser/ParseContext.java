// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.

package com.yahoo.fili_config_utils.config.metric.parser;

import com.yahoo.fili_config_utils.config.provider.MakerBuilder;
import com.yahoo.bard.webservice.data.dimension.DimensionDictionary;
import com.yahoo.bard.webservice.data.metric.MetricDictionary;

/**
 * Metric parser context: metric name, metric dictionary, etc.
 */
public class ParseContext {
    protected String name;

    // All lookups happen here; this is where the temporary metrics all end up
    protected final MetricDictionary scopedDict;

    protected final DimensionDictionary dimensionDictionary;
    protected final MakerBuilder makerBuilder;
    protected final String metricDefinition;

    /**
     * Constructor.
     *
     * @param metricName the metric name
     * @param metricDefinition the metric definition string (to be parsed)
     * @param dict the metric dictionary
     * @param makerBuilder the metric maker builder
     * @param dimensionDictionary the dimension dictionary
     */
    public ParseContext(
            String metricName,
            String metricDefinition,
            MetricDictionary dict,
            MakerBuilder makerBuilder,
            DimensionDictionary dimensionDictionary
    ) {
        this.name = metricName;

        // Get temporary scope for use here and make sure it's empty
        this.scopedDict = dict.getScope("MetricParserScope");
        this.scopedDict.clearLocal();

        this.makerBuilder = makerBuilder;
        this.dimensionDictionary = dimensionDictionary;
        this.metricDefinition = metricDefinition;
    }
}
