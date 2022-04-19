/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.snowflake;

import io.trino.plugin.jdbc.BaseJdbcClient;
import io.trino.plugin.jdbc.BaseJdbcConfig;
import io.trino.plugin.jdbc.ConnectionFactory;
import io.trino.plugin.jdbc.JdbcIdentity;
import io.trino.plugin.jdbc.JdbcSplit;
import io.trino.plugin.jdbc.WriteMapping;
import io.trino.spi.connector.ConnectorSession;
import io.trino.spi.type.Type;

import javax.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.BiFunction;

import static io.trino.plugin.jdbc.StandardColumnMappings.timestampWriteFunctionUsingSqlTimestamp;
import static io.trino.spi.type.TimestampType.TIMESTAMP_NANOS;

public class SnowflakeClient
        extends BaseJdbcClient
{
    @Inject
    public SnowflakeClient(BaseJdbcConfig config, ConnectionFactory connectionFactory)
    {
        super(config, "\"", connectionFactory);
    }

    @Override
    public boolean isLimitGuaranteed(ConnectorSession session)
    {
        return true;
    }

    @Override
    protected Optional<BiFunction<String, Long, String>> limitFunction()
    {
        return Optional.of((sql, limit) -> sql + " LIMIT " + limit);
    }

    @Override
    public WriteMapping toWriteMapping(ConnectorSession session, Type type)
    {
        if (TIMESTAMP_NANOS.equals(type)) {
            return WriteMapping.longMapping("timestamp", timestampWriteFunctionUsingSqlTimestamp(TIMESTAMP_NANOS));
        }

        return super.toWriteMapping(session, type);
    }

    // Required since SnowflakeConnectionV1 does not support setReadOnly
    @Override
    public Connection getConnection(JdbcIdentity identity, JdbcSplit split) throws SQLException
    {
        return connectionFactory.openConnection(identity);
    }
}
