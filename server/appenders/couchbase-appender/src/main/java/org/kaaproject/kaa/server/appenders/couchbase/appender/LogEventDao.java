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

package org.kaaproject.kaa.server.appenders.couchbase.appender;

import java.util.List;

import org.kaaproject.kaa.common.dto.logs.LogEventDto;
import org.kaaproject.kaa.server.common.log.shared.avro.gen.RecordHeader;

public interface LogEventDao {

    List<LogEvent> save(RecordHeader header, List<LogEventDto> logEventDtos);
    
    void close();
    
}
