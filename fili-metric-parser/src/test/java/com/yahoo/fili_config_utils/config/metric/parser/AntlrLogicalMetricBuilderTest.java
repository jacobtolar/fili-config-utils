// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.config.metric.parser;

import com.yahoo.bard.webservice.data.config.dimension.DimensionConfig;
import com.yahoo.bard.webservice.data.dimension.Dimension;
import com.yahoo.bard.webservice.data.dimension.DimensionDictionary;
import com.yahoo.bard.webservice.data.dimension.DimensionField;
import com.yahoo.bard.webservice.data.dimension.DimensionRow;
import com.yahoo.bard.webservice.data.dimension.KeyValueStore;
import com.yahoo.bard.webservice.data.dimension.MapStoreManager;
import com.yahoo.bard.webservice.data.dimension.SearchProvider;
import com.yahoo.bard.webservice.data.dimension.impl.KeyValueStoreDimension;
import com.yahoo.bard.webservice.data.dimension.impl.NoOpSearchProviderManager;
import com.yahoo.bard.webservice.data.metric.LogicalMetric;
import com.yahoo.bard.webservice.data.metric.MetricDictionary;
import com.yahoo.fili_config_utils.config.provider.MakerBuilder;
import com.yahoo.fili_config_utils.config.provider.descriptor.MakerDescriptor;
import com.yahoo.fili_config_utils.config.provider.descriptor.MetricDescriptor;
import com.yahoo.fili_config_utils.config.provider.impl.DimensionConfigImpl;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AntlrLogicalMetricBuilderTest {

    // FIXME: No assertions in this test yet, just for playing around in intellij.
    @Test
    public void simpleParserTest() {
        DimensionDictionary dimensionDictionary = new DimensionDictionary();
        dimensionDictionary.add(new KeyValueStoreDimension(
            new DimensionConfigImpl(
                "foo",
                "longDimName",
                "c",
                "foo",
                "desc",
                new LinkedHashSet<DimensionField>(),
                new LinkedHashSet<DimensionField>(),
                MapStoreManager.getInstance("foo"),
                NoOpSearchProviderManager.getInstance("foo"),
                true,
                KeyValueStoreDimension.class)
        ));

        MakerDescriptor[] arr = {};
        MakerBuilder makerBuilder = new MakerBuilder(Arrays.asList(arr));
        MetricDictionary metricDictionary = new MetricDictionary();


        AntlrLogicalMetricBuilder metricBuilder = new AntlrLogicalMetricBuilder(dimensionDictionary, makerBuilder, metricDictionary);
        LogicalMetric
            result =
            metricBuilder.buildMetric(new MetricDescriptor("impressions", "impressions", "cat", "desc", "longSum(impressions) + longSum(clicks) + 7", false,
                                                           Collections.EMPTY_SET));

        metricDictionary.add(result);


        AntlrLogicalMetricBuilder metricBuilder2 = new AntlrLogicalMetricBuilder(dimensionDictionary, makerBuilder, metricDictionary);
        LogicalMetric result2 = metricBuilder.buildMetric(new MetricDescriptor("fimp", "fimp", "cat", "desc", "longSum(otherMetric) | foo == 1", false,
                                                       Collections.EMPTY_SET));
        System.out.println("done");
    }
}
