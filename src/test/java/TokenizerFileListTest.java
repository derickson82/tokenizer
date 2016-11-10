import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class TokenizerFileListTest
{
    Tokenizer tokenizer = new Tokenizer();

    @Test
    public void test()
    {
        List<String> fileNames = tokenizer.listTokens(Paths.get("src/test/resources/filelist.txt"))
                .collect(toList());

        assertThat(fileNames, contains("test1.txt", "test2.txt"));
    }

}
