package chain.of.transformations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class TransformationsWithOrder {

    private static final Function<String, String> toLowerCase = String::toLowerCase;
    private static final Function<String, String> toUpperCase = String::toUpperCase;
    private static final Function<String, String> trim = String::trim;
    private static final Function<String, String> dropWordHello = s -> s.replaceAll("hello", "");
    private static final Map<String,Function<String, String>> transformations = Map
            .ofEntries(
                    Map.entry("toLowerCase", toLowerCase),
                    Map.entry("toUpperCase", toUpperCase),
                    Map.entry("trim", trim),
                    Map.entry("dropWordHello", dropWordHello)
            );



    public static void main(String[] args) {
        final var transformationsOrder = List.of("toLowerCase", "dropWordHello", "toUpperCase", "trim");

        final var chain = transformationsOrder // Let's start from the transformation's order
                .stream()
                /* Let's try to map each value in the list to an actual transformation operation.
                   If we don't found it (due to a mistake, typo, etc..) let's return null.
                   They will be handled in next operations */
                .map(e -> transformations.getOrDefault(e, null))
                /* Filtering away null mean that configured some transformation that it will actually not executed.
                   So it can be actually risky this way to handle this scenario. But let's proceed like that for simplicity
                   At the end there is not a right way to do it. It's a trade-off that depends on the scenario in which we are. */
                .filter(Objects::nonNull)
                .reduce(Function.identity(), Function::andThen);


        System.out.println(chain.apply("Hi, I'm Jacopo!"));
        System.out.println(chain.apply("Welcome to my"));
        System.out.println(chain.apply("Hello World"));
    }

}
