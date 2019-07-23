package mn.graphql.datafetcher;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import graphql.schema.DataFetcher;
import lombok.val;
import mn.graphql.pojo.Link;
import mn.graphql.repository.LinkRepository;

@Singleton
public class DataFetcherProvider {

    @Inject
    private LinkRepository linkRepository;
    
    public DataFetcher<List<Link>> getAllLinksDataFetcher() {

        return dataFetchingEnvironment -> this.linkRepository.getLinks();
    }

    public DataFetcher<Link> createLinkDataFetcher() {

        return dataFetchingEnvironment -> {
            
            final String url = dataFetchingEnvironment.getArgument("url");
            final String description = dataFetchingEnvironment.getArgument("description");
            val link = new Link(url, description);
            this.linkRepository.saveLink(link);
            return link;
         };
    }
}
