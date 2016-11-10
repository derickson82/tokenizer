import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class TokenizerCleanupTest
{
    private final String input;
    private final String expected;

    @Parameters
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][] {
                { "test", "test" },
                { "!test!", "test" },
                { "!@#$%^&*()*", "" },
                { "that's", "that's" },
                { "SHOUTING", "shouting" },
                { null, "" }
        });
    }

    public TokenizerCleanupTest(String input, String expected)
    {
        this.input = input;
        this.expected = expected;
    }

    Tokenizer tokenizer = new Tokenizer();

    @Test
    public void testTokenCleanup()
    {
        assertThat(tokenizer.cleanup(input), equalTo(expected));
    }
}
