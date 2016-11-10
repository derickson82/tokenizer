import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Tokenizer
{

    public static void main(String... args)
    {
        if(args.length != 1)
        {
            throw new IllegalArgumentException("Specify the name of a file");
        }

        Map<String, Long> tokenized = new Tokenizer().tokenize(args[0]);

        tokenized.entrySet().stream()
                .sorted(Map.Entry.<String, Long> comparingByValue().reversed())
                .forEach(entry -> {
                    System.out.println(String.format("%s %d", entry.getKey(), entry.getValue()));
                });
    }

    public Map<String, Long> tokenize(String masterFilePath)
    {
        Path originalPath = Paths.get(masterFilePath);
        if(!Files.exists(originalPath))
        {
            throw new RuntimeException("Specified file does not exist");
        }

        Map<String, Long> tokenized = listTokens(originalPath)
                .map(originalPath::resolveSibling)
                .flatMap(this::listTokens)
                .map(this::cleanup)
                .collect(groupingBy(Function.identity(), counting()));

        return tokenized;
    }

    public Stream<String> listTokens(Path filePath)
    {
        if(Files.exists(filePath))
        {
            try(Scanner scanner = new Scanner(filePath, StandardCharsets.UTF_8.name()))
            {
                Stream.Builder<String> streamBuilder = Stream.builder();
                scanner.forEachRemaining(streamBuilder::accept);
                return streamBuilder.build();
            }
            catch(IOException e)
            {
                throw new RuntimeException(String.format("Failed to read file: %s", filePath), e);
            }
        }
        return Stream.empty();
    }

    public String cleanup(String word)
    {
        return word == null ? "" : word.replaceAll("^[^a-zA-Z0-9]*", "")
                .replaceAll("[^a-zA-Z0-9]$", "")
                .toLowerCase();
    }

}
