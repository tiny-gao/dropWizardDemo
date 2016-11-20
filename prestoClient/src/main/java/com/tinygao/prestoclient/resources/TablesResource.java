package com.tinygao.prestoclient.resources;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.tinygao.prestoclient.metadata.Column;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by gsd on 2016/11/20.
 */
@Path("/api/table")
public class TablesResource {

    //private final Column column;
    //private final String defaultCatalog;
    @Inject
    public TablesResource(
            /*final Column columnCache,
            @Named("default-catalog") final String defaultCatalog*/)
    {
       // this.column = columnCache;
        //this.defaultCatalog = defaultCatalog;
    }
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<String> index() {
        return Arrays.asList("Day 12: OpenCV--Face Detection for Java Developers",
                "https://www.openshift.com/blogs/day-12-opencv-face-detection-for-java-developers");
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{schema}/{tableName}/columns")
    public Response getTableColumns(
            @PathParam("schema") String schema,
            @PathParam("tableName") String tableName) {
        Column c = new Column();
        return Response.ok( c.queryColumns(format("SHOW COLUMNS FROM  %s.%s", schema, tableName))).build();
    }
}
