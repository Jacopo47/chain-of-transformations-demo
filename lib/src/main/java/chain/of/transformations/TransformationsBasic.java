package chain.of.transformations;

import java.util.List;
import java.util.function.Function;

public class TransformationsBasic {

    private static final Function<String, String> toLowerCase = String::toLowerCase;
    private static final Function<String, String> toUpperCase = String::toUpperCase;
    private static final Function<String, String> trim = String::trim;
    private static final Function<String, String> dropWordHello = s -> s.replaceAll("hello", "");
    private static final List<Function<String, String>> transformations = List.of(
            toLowerCase,
            trim,
            dropWordHello,
            toUpperCase
    );

    public static void main(String[] args) {
        final var chain = transformations
                .stream()
                .reduce(Function.identity(), Function::andThen);

        System.out.println(chain.apply("Hi, I'm Jacopo!"));
        System.out.println(chain.apply("Welcome to my"));
        System.out.println(chain.apply("Hello World"));
    }

}
