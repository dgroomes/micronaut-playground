package dgroomes.sql;

import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;

import java.util.List;

@MappedEntity("observations")
public record Observation(
        String description,
        @TypeDef(type = DataType.STRING_ARRAY)
        List<String> notes) {
}
