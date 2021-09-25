package dgroomes.sql;

import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "observations")
public record Observation(
        @Id
        @GeneratedValue
        Long id,
        String description,
        @TypeDef(type = DataType.STRING_ARRAY)
        List<String> notes) {
}
