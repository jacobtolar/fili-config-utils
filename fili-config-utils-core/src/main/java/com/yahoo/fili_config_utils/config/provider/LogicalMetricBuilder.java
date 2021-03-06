// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.

package com.yahoo.fili_config_utils.config.provider;

import com.yahoo.fili_config_utils.config.provider.descriptor.MetricDescriptor;
import com.yahoo.bard.webservice.data.dimension.DimensionDictionary;
import com.yahoo.bard.webservice.data.metric.LogicalMetric;
import com.yahoo.bard.webservice.data.metric.MetricDictionary;

/**
 * Thing that is able to build a logical metric.
 */
public abstract class LogicalMetricBuilder {

    protected final DimensionDictionary dimensionDictionary;
    protected final MakerBuilder makerBuilder;
    protected final MetricDictionary localDictionary;

    /**
     * Construct a new logical metric builder.
     *
     * @param dimensionDictionary  The dimension dictionary
     * @param builder  The Metric Maker builder
     * @param localDictionary  The metric dictionary
     */
    public LogicalMetricBuilder(
            DimensionDictionary dimensionDictionary,
            MakerBuilder builder,
            MetricDictionary localDictionary) {
        this.dimensionDictionary = dimensionDictionary;
        this.makerBuilder = builder;
        this.localDictionary = localDictionary;
    }

    /**
     * Build a logical metric, given its configuration.
     *
     * @param metric  The metric configuration
     *
     * @return A logical metric. Up to the caller to add to the real metric dictionary.
     */
    public abstract LogicalMetric buildMetric(MetricDescriptor metric);
}
