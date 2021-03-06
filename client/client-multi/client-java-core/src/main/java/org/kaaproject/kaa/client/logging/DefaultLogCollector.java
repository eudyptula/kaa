/**
 *  Copyright 2014-2016 CyberVision, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.kaaproject.kaa.client.logging;

import java.io.IOException;

import javax.annotation.Generated;

import org.kaaproject.kaa.client.channel.FailoverManager;
import org.kaaproject.kaa.client.channel.KaaChannelManager;
import org.kaaproject.kaa.client.channel.LogTransport;
import org.kaaproject.kaa.client.context.ExecutorContext;
import org.kaaproject.kaa.client.logging.future.RecordFuture;
import org.kaaproject.kaa.schema.base.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reference implementation of @see LogCollector
 *
 * @author Andrew Shvayka
 */
@Generated("DefaultLogCollector.java.template")
public class DefaultLogCollector extends AbstractLogCollector {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLogCollector.class);

    public DefaultLogCollector(LogTransport transport, ExecutorContext executorContext,
                               KaaChannelManager channelManager, FailoverManager failoverManager) {
        super(transport, executorContext, channelManager, failoverManager);
    }

    @Override
    public RecordFuture addLogRecord(final Log record) {
        final RecordFuture future = new RecordFuture();
        executorContext.getApiExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BucketInfo bucketInfo = storage.addLogRecord(new LogRecord(record));
                    bucketInfoMap.put(bucketInfo.getBucketId(), bucketInfo);
                    addDeliveryFuture(bucketInfo, future);
                } catch (IOException e) {
                    LOG.warn("Can't serialize log record {}, exception catched: {}", record, e);
                }

                uploadIfNeeded();
            }
        });

        return future;
    }
}
