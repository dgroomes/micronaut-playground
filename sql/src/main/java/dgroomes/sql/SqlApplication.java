package dgroomes.sql;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.data.connection.jdbc.advice.DelegatingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * A simple Micronaut program that connects to a SQL database. It makes use of the Micronaut Data project to do the heavy
 * lifting of connecting to the database, interacting with it, and mapping between SQL data (rows) and Java data (objects).
 */
public class SqlApplication {

    private static final Logger log = LoggerFactory.getLogger(SqlApplication.class);

    // Terminal ANSI escape codes. See https://stackoverflow.com/a/28938235
    private static final String UNDERLINED_BLACK = "\033[4;30m";
    private static final String NO_COLOR = "\033[0m";

    public static void main(String[] args) throws Exception {
        var builder = ApplicationContext.builder(SqlApplication.class, Environment.CLI);
        try (var ctx = builder.build()) {
            ctx.start();
            var repo = ctx.getBean(ObservationsRepository.class);

            makeObservations(repo);
            listObservations(repo);
            wordCount(ctx);
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
        log.info("Creating some observations and inserting them into the 'observations' table...\n");
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
        log.info("Listing all of the observations in the 'observations' table...");

        var observationsTable = String.format("%s%-50s%s%s%n", UNDERLINED_BLACK, "Description", "Notes", NO_COLOR);
        observationsTable += repo.findAll()
                .stream()
                .map(observation -> String.format("%-50s%s", observation.description(), observation.notes()))
                .collect(Collectors.joining("\n"));

        log.info("\n{}\n\n", observationsTable);
    }

    /**
     * Do a "word count" for the words contained in the "notes" column across all rows in the "observations" table.
     * <p>
     * This query is a bit complex. It would be much easier to write this aggregation in Java code, but it's
     * expensive (network, memory) to do that because it requires shipping all of the row data from the
     * Postgres database into the Java process. For large data, that's too cost prohibitive. So this is an
     * example that shows how to execute hand-written SQL queries in a Micronaut/Postgres context.
     */
    private static void wordCount(ApplicationContext ctx) throws SQLException {
        log.info("Computing a word count...");
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
        builder.append(String.format("%s%-12s%s%s%n", UNDERLINED_BLACK, "Word", "Count", NO_COLOR)); // Header row
        while (resultSet.next()) {
            var word = resultSet.getObject(1);
            var count = resultSet.getObject(2);
            var formatString = "%-12s%s%n";
            builder.append(String.format(formatString, word, count));
        }
        var wordCountTable = builder.toString();

        log.info("\n{}", wordCountTable);
    }
}
