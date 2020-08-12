package guru.springframework;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InlineMocksTest {

    @Test
    void testMock() {
        Map mapMock = mock(Map.class);

        when(mapMock.size()).thenReturn(2);

        assertEquals(mapMock.size(), 2);
    }
}
