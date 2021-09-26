package dgroomes.sql;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;

import java.util.List;

@MappedEntity("observations")
public record Observation(
        @Id
        @GeneratedValue
        Long id,
        String description,
        @TypeDef(type = DataType.STRING_ARRAY)
        List<String> notes) {
}
