package mn.graphql.factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.inject.Singleton;

import graphql.GraphQL;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import lombok.val;
import mn.graphql.datafetcher.DataFetcherProvider;

@Factory
public class GraphQLFactory {
    
    @Bean
    @Singleton
    public GraphQL graphQL(
        final ResourceResolver resourceResolver,
        final DataFetcherProvider dataFetcherProvider) {
        
        val schemaParser = new SchemaParser();
        val schemaGenerator = new SchemaGenerator();

        // Parse the schema.
        val typeRegistry = new TypeDefinitionRegistry();
        typeRegistry.merge(schemaParser.parse(new BufferedReader(new InputStreamReader(
            resourceResolver.getResourceAsStream("classpath:schema.graphqls").get()
        ))));

        // Create the runtime wiring.
        val runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .type(TypeRuntimeWiring
                .newTypeWiring("Query")
                .dataFetcher("allLinks", dataFetcherProvider.getAllLinksDataFetcher()))
            .type(TypeRuntimeWiring
                .newTypeWiring("Mutation")
                .dataFetcher("createLink", dataFetcherProvider.createLinkDataFetcher()))
            .build();

        // Create the executable schema.
        val graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

        // Return the GraphQL bean.
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
