package com.rso.microservice.util;

import java.io.IOException;

public class GraphQLSchemaReaderUtil {

    public static String getSchemaFromFileName(final String filename) throws IOException {
        return new String(GraphQLSchemaReaderUtil.class.getClassLoader().getResourceAsStream("graphql/" + filename + ".graphql").readAllBytes());
    }

}
