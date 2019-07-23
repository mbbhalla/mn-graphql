package mn.graphql.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import lombok.Getter;
import mn.graphql.pojo.Link;

@Singleton
@Getter
public class LinkRepository {
    
    private final List<Link> links;

    public LinkRepository() {
        links = new ArrayList<>();
        
        //add some links to start off with
        links.add(new Link("http://howtographql.com", "Your favorite GraphQL page"));
        links.add(new Link("http://graphql.org/learn/", "The official docs"));
        links.add(new Link("http://a.org/learn/", "A Org"));
    }
   
    public void saveLink(Link link) {
        links.add(link);
    }
}