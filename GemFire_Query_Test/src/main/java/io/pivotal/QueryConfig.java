package io.pivotal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 505007855 on 3/8/2017.
 */

@Configuration
public class QueryConfig {


    @Value("${flatFileDelimiter}")
    private String flatFileDelimiter;





    public String getFlatFileDelimiter() {
        return flatFileDelimiter;
    }

}