package dgroomes.sql;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple Micronaut program that connects to a SQL database. It makes use of the Micronaut Data project to do the heavy
 * lifting of connecting to the database, interacting with it, and mapping between SQL data (rows) and Java data (objects).
 */
public class SqlApplication {

    private static final Logger log = LoggerFactory.getLogger(SqlApplication.class);

    public static void main(String[] args) {
        var builder = ApplicationContext.builder(SqlApplication.class, Environment.CLI);
        try (var ctx = builder.build()) {
            ctx.start();
            var repo = ctx.getBean(ObservationsRepository.class);

            makeObservations(repo);
            listObservations(repo);
            log.info("\n\n");
        }
    }

    /**
     * Arrange the elements of a list into a random order. This does not side-effect the given list. It returns a new one.
     *
     * @param list the given list to randomize
     * @return a randomized version of the original list
     */
    private static <T> List<T> randomize(List<T> list) {
        list = new ArrayList<>(list);
        var random = new Random();
        var randomized = new ArrayList<T>();
        for (int elementsRemaining = list.size(); elementsRemaining > 0; elementsRemaining--) {
            var randomIndex = random.nextInt(elementsRemaining);
            var randomEl = list.remove(randomIndex);
            randomized.add(randomEl);
        }
        return randomized;
    }

    /**
     * Make some random observations and insert them into the "observations" Postgres table.
     *
     * @param repo a Micronaut repository object over the "observations" table
     */
    private static void makeObservations(ObservationsRepository repo) {
        var items = randomize(List.of("Java", "Micronaut", "SQL")).iterator();
        var descriptors = randomize(List.of("is the best!", "is amazing!", "rocks!")).iterator();

        var notes = List.of(
                String.format("%s %s", items.next(), descriptors.next()),
                String.format("%s %s", items.next(), descriptors.next()),
                String.format("%s %s", items.next(), descriptors.next()));

        repo.save(new Observation("random software observations", notes));
    }

    /**
     * List all observations from the "observations" table in the Postgres table
     *
     * @param repo a Micronaut repository object over the "observations" table
     */
    private static void listObservations(ObservationsRepository repo) {

        var observations = repo.findAll();
        log.info("\n\n");
        log.info("Found the following observations...");
        for (Observation observation : observations) {
            log.info(observation.toString());
        }
    }
}
