package basic;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
class BasicTest {

    @Inject
    String myMessage;

    @Test
    void testItWorks() {
        Assertions.assertEquals("Hello world!", myMessage);
    }
}
