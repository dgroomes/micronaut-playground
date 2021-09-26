package dgroomes.sql;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ObservationsRepository {

    Observation save(Observation observation);

    List<Observation> findAll();
}
