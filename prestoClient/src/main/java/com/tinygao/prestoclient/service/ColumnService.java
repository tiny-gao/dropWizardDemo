package com.tinygao.prestoclient.service;

import com.tinygao.prestoclient.configuration.ColumnConfiguration;
import com.tinygao.prestoclient.resources.TablesResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by gsd on 2016/11/20.
 */
public class ColumnService extends Application<ColumnConfiguration> {
    public static void main(String[] args) throws Exception {
        new ColumnService().run(args);
    }
    @Override
    public String getName() {
        return "tinygao-blog";
    }

    @Override
    public void initialize(Bootstrap<ColumnConfiguration> bootstrap) {
    }
    @Override
    public void run(ColumnConfiguration ColumnConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new TablesResource());
    }
}
