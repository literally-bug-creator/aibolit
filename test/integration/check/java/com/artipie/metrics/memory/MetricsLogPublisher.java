/*
 * SPDX-FileCopyrightText: Copyright (c) 2020 artipie.com
 * SPDX-FileCopyrightText: Copyright (c) 2019-2025 Aibolit
 * SPDX-License-Identifier: MIT
 */
package com.artipie.metrics.memory;

import java.time.Duration;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

/**
 * Periodic publisher of {@link InMemoryMetrics} to log.
 *
 * @since 0.9
 * @todo #231:30min Support gauge publishing in `MetricsLogPublisher`.
 *  `InMemoryMetrics` contain gauges along counters.
 *  Gauges should be published the same way as counters.
 *  Should be done after https://github.com/artipie/artipie/issues/267
 * @todo #231:30min Add tests for `MetricsLogPublisher`.
 *  It should be tested that the publisher runs periodically, collects fresh metrics data
 *  and logs the data as expected.
 */
public class MetricsLogPublisher {

    /**
     * Logger to use for publishing.
     */
    @SuppressWarnings("PMD.LoggerIsNotStaticFinal")
    private final Logger logger;

    /**
     * Metrics for publishing.
     */
    private final InMemoryMetrics metrics;

    /**
     * Period.
     */
    private final Duration period;

    /**
     * Ctor.
     *
     * @param logger Logger to use for publishing.
     * @param metrics Metrics for publishing.
     * @param period Period.
     */
    public MetricsLogPublisher(
        final Logger logger,
        final InMemoryMetrics metrics,
        final Duration period
    ) {
        this.logger = logger;
        this.metrics = metrics;
        this.period = period;
    }

    /**
     * Start periodic publishing.
     */
    public void start() {
        final long millis = this.period.toMillis();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
            this::publish,
            millis,
            millis,
            TimeUnit.MILLISECONDS
        );
    }

    /**
     * Publish metrics to log.
     */
    private void publish() {
        final Map<String, InMemoryCounter> counters = new TreeMap<>(this.metrics.counters());
        if (!counters.isEmpty()) {
            final StringBuilder message = new StringBuilder("Counters:");
            for (final Map.Entry<String, InMemoryCounter> entry : counters.entrySet()) {
                message.append('\n')
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue().value());
            }
            this.logger.info(message.toString());
        }
    }
}
