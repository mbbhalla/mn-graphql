package mn.graphql.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Link {

    private final String url;
    private final String description;

}