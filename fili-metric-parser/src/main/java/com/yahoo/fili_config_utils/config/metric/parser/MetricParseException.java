// Copyright 2017 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.

package com.yahoo.fili_config_utils.config.metric.parser;

/**
 * Unchecked exception thrown when parsing metric.
 */
public class MetricParseException extends RuntimeException {

    /**
     * Construct a new MetricParseException.
     *
     * @param message the exception message
     */
   public MetricParseException(String message) {
      super(message);
   }
}
