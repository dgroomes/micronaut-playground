package dgroomes.sql;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A simple Micronaut program that connects to a SQL database
 */
public class SqlApplication {

    private static final Logger log = LoggerFactory.getLogger(SqlApplication.class);

    public static void main(String[] args) {
        var builder = ApplicationContext.builder(SqlApplication.class, Environment.CLI);
        try (var ctx = builder.build()) {
            ctx.start();
            var repo = ctx.getBean(ObservationsRepository.class);

// I'm not sure I'll need this, but this is how you get a reference to the DataSource object that you can use to make queries
// straight to the database. It requires this "unwrapping" stuff.
//            var dataSourceTopLevel = ctx.getBean(DataSource.class);
//            var dataSource = DelegatingDataSource.unwrapDataSource(dataSourceTopLevel);

            repo.save(new Observation(null, "expected observation", List.of("A", "B", "C", "123")));

            var observations = repo.findAll();
            log.info("\n\n");
            log.info("Found the following observations...");
            for (Observation observation : observations) {
                log.info(observation.toString());
            }
            log.info("\n\n");
        }
    }
}
