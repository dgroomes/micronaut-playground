package dgroomes.basic;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class BasicTest {

    @Inject
    String myMessage;

    @Test
    void testItWorks() {
        Assertions.assertEquals("Hello world!", myMessage);
    }
}
