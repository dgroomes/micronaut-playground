package dgroomes.sql;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.transaction.jdbc.DelegatingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple Micronaut program that connects to a SQL database. It makes use of the Micronaut Data project to do the heavy
 * lifting of connecting to the database, interacting with it, and mapping between SQL data (rows) and Java data (objects).
 */
public class SqlApplication {

    private static final Logger log = LoggerFactory.getLogger(SqlApplication.class);

    public static void main(String[] args) throws Exception {
        var builder = ApplicationContext.builder(SqlApplication.class, Environment.CLI);
        try (var ctx = builder.build()) {
            log.info("Starting the application...\n\n");
            ctx.start();
            var repo = ctx.getBean(ObservationsRepository.class);
            log.info("\n\n");

            makeObservations(repo);
            listObservations(repo);
            adhocQuery(ctx);
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
        log.info("Creating some observations and inserting them into the database...\n\n");
        var items = randomize(List.of("Java", "Micronaut", "SQL")).iterator();
        var descriptors = randomize(List.of("is the best!", "is amazing!", "rocks!")).iterator();

        var notes = List.of(
                String.format("%s %s", items.next(), descriptors.next()),
                String.format("%s %s", items.next(), descriptors.next()),
                String.format("%s %s", items.next(), descriptors.next()));

        repo.save(new Observation("random software observations", notes));
        log.info("\n\n");
    }

    /**
     * List all observations from the "observations" table in the Postgres table
     *
     * @param repo a Micronaut repository object over the "observations" table
     */
    private static void listObservations(ObservationsRepository repo) {
        log.info("Listing all of the observations in the 'observations' table...\n\n");

        var observations = repo.findAll();
        log.info("Found the following observations:\n");
        for (Observation observation : observations) {
            log.info(observation.toString());
        }
        log.info("\n\n");
    }

    /**
     * Do a "word count" for the words contained in the "notes" column across all rows in the "observations" table.
     * <p>
     * This query is a bit complex. It would be much easier to write this aggregation in Java code, but it's
     * expensive (network, memory) to do that because it requires shipping all of the row data from the
     * Postgres database into the Java process. For large data, that's too cost prohibitive. So this is an
     * example that shows how to execute hand-written SQL queries in a Micronaut/Postgres context.
     */
    private static void adhocQuery(ApplicationContext ctx) throws SQLException {
        log.info("Computing a word count...\n\n");
        // This query flattens the "notes" array value across all rows and then splits each note into individual
        // words. The "unnest" function flattens the arrays and then the "regexp_split_to_table" function splits the note
        // strings into individual words. The result of this operation goes into a Common Table Expression (CTE) named
        // "note_words". Then, a simple count aggregation is applied to the "note_words" table to get the final word
        // count.
        var sql = """
                with note_words as (select regexp_split_to_table(unnest(notes), E'\\\\s+') word from observations),
                word_count as (select word, count(*) count from note_words group by word)
                select * from word_count order by count desc
                """;

        // This is how you get a reference to the DataSource object that you can use to make queries straight to the
        // database. You need to "unwrap" the underlying DataSource from the top-level DataSource. If you don't, then
        // the program will throw a "io.micronaut.transaction.exceptions.NoTransactionException" when you try to execute
        // a query using the still-wrapped data source.
        var dataSourceTopLevel = ctx.getBean(DataSource.class);
        var dataSource = DelegatingDataSource.unwrapDataSource(dataSourceTopLevel);
        var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery(sql);

        // Collect the results into a string that's formatted like a table. There are two columns: "Word" and "Count"
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-12s%sn%n", "Word", "Count")); // Header row
        while (resultSet.next()) {
            var word = resultSet.getObject(1);
            var count = resultSet.getObject(2);
            var formatString = "%-12s%s%n";
            builder.append(String.format(formatString, word, count));
        }
        var wordCountTable = builder.toString();

        log.info("Computed the following 'word count' across all words in the observation notes:\n\n{}", wordCountTable);
    }
}
