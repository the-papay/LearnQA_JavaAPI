package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShortPhraseTest {

    @Test
    public void checkStringLengthTest() {
        String str = "1234567890qwertyz";

        Assertions.assertTrue(str.length() > 15, "Test passed, string length more that 15 symbols");

    }

}
