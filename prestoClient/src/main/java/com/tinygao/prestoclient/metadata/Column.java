package com.tinygao.prestoclient.metadata;

import com.facebook.presto.client.ClientTypeSignature;
import com.facebook.presto.client.QueryResults;
import com.facebook.presto.client.StatementClient;
import com.facebook.presto.spi.type.TypeSignature;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.tinygao.prestoclient.core.QueryClient;
import com.tinygao.prestoclient.core.QueryRunner;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Created by gsd on 2016/11/20.
 */
@Data
@Slf4j
public class Column {
    private  String name;
    private  String type;
    private  ClientTypeSignature typeSignature;
    private  QueryRunner.QueryRunnerFactory queryRunnerFactory;

    public Column(){}
    @JsonCreator
    public Column(
            final QueryRunner.QueryRunnerFactory queryRunnerFactory,
            @JsonProperty("name") String name,
            @JsonProperty("type") String type,
            @JsonProperty("typeSignature") ClientTypeSignature typeSignature)
    {
        this.queryRunnerFactory = requireNonNull(queryRunnerFactory, "name is null");
        this.name = requireNonNull(name, "name is null");
        this.type = requireNonNull(type, "type is null");
        this.typeSignature = typeSignature;
    }

    @JsonCreator
    public Column(
            @JsonProperty("name") String name,
            @JsonProperty("type") String type,
            @JsonProperty("typeSignature") ClientTypeSignature typeSignature)
    {
        this.name = requireNonNull(name, "name is null");
        this.type = requireNonNull(type, "type is null");
        this.typeSignature = typeSignature;
    }
    public List<HiveColumn> queryColumns(String query)
    {
        final ImmutableList.Builder<HiveColumn> cache = ImmutableList.builder();
        QueryRunner queryRunner = queryRunnerFactory.create();
        QueryClient queryClient = new QueryClient(queryRunner, io.dropwizard.util.Duration.seconds(60), query);

        try {
            queryClient.executeWith(new Function<StatementClient, Void>() {
                @Nullable
                @Override
                public Void apply(StatementClient client)
                {
                    QueryResults results = client.current();
                    if (results.getData() != null) {
                        for (List<Object> row : results.getData()) {
                            Column column = new Column((String) row.get(0), (String) row.get(1), new ClientTypeSignature(TypeSignature.parseTypeSignature((String)row.get(1))));
                            boolean isNullable = false;// (Boolean) row.get(2);
                            boolean isPartition = "Partition Key".equals(row.get(2));

                            cache.add(HiveColumn.fromColumn(column, isNullable, isPartition));
                        }
                    }

                    return null;
                }
            });
        }
        catch (QueryClient.QueryTimeOutException e) {
            log.error("Caught timeout loading columns", e);
        }

        return cache.build();
    }

}
