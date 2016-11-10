import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;

public class TokenizerTest
{

    Tokenizer tokenizer = new Tokenizer();

    @Test
    public void testTokenization()
    {
        Map<String, Long> tokenize = tokenizer.tokenize("src/test/resources/filelist.txt");

        assertThat(tokenize, allOf(
                hasEntry("but", 1L),
                hasEntry("very", 2L),
                hasEntry("a", 4L),
                hasEntry("test", 4L),
                hasEntry("hasn't", 1L),
                hasEntry("underscore", 1L)));
    }
}
