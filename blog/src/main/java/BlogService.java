import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by gsd on 2016/11/20.
 */
public class BlogService extends Application<BlogConfiguration> {
    public static void main(String[] args) throws Exception {
        new BlogService().run(args);
    }
    @Override
    public String getName() {
        return "tinygao-blog";
    }

    @Override
    public void initialize(Bootstrap<BlogConfiguration> bootstrap) {
    }
    @Override
    public void run(BlogConfiguration blogConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new IndexResource());
    }
}
