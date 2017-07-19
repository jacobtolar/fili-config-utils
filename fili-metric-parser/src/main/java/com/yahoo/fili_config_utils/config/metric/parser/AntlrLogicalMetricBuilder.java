// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.fili_config_utils.config.metric.parser;

import com.yahoo.bard.webservice.data.dimension.DimensionDictionary;
import com.yahoo.bard.webservice.data.metric.LogicalMetric;
import com.yahoo.bard.webservice.data.metric.MetricDictionary;
import com.yahoo.fili_config_utils.config.metric.antlrparser.FiliMetricLexer;
import com.yahoo.fili_config_utils.config.metric.antlrparser.FiliMetricParser;
import com.yahoo.fili_config_utils.config.provider.LogicalMetricBuilder;
import com.yahoo.fili_config_utils.config.provider.MakerBuilder;
import com.yahoo.fili_config_utils.config.provider.descriptor.MetricDescriptor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

public class AntlrLogicalMetricBuilder extends LogicalMetricBuilder {

    public AntlrLogicalMetricBuilder(DimensionDictionary dimensionDictionary, MakerBuilder makerBuilder, MetricDictionary metricDictionary) {
        super(dimensionDictionary, makerBuilder, metricDictionary);
    }

    @Override
    public LogicalMetric buildMetric(MetricDescriptor metric) {
        ParseContext context = new ParseContext(
            metric.getName(),
            metric.getDefinition(),
            this.localDictionary,
            makerBuilder,
            dimensionDictionary
        );

        CharStream charStream = new ANTLRInputStream(context.metricDefinition);
        FiliMetricLexer lexer = new FiliMetricLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        FiliMetricParser parser = new FiliMetricParser(tokens);

        FiliExpressionVisitor visitor = new FiliExpressionVisitor(context);
        LogicalMetric result = visitor.visitFiliExpression(parser.filiExpression());

        // keep track of metrics we've created
        localDictionary.add(result);
        return result;
    }
}
