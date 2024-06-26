package oop.project.cli;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Scenarios {

    /**
     * Parses and returns the arguments of a command (one of the scenarios
     * below) into a Map of names to values. This method is provided as a
     * starting point that works for most groups, but depending on your command
     * structure and requirements you may need to make changes to adapt it to
     * your needs - use whatever is convenient for your design.
     */
    public static Map<String, Object> parse(String command) throws ArgumentParserException {
        //This assumes commands follow a similar structure to unix commands,
        //e.g. `command [arguments...]`. If your project uses a different
        //structure, e.g. Lisp syntax like `(command [arguments...])`, you may
        //need to adjust this a bit to work as expected.
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "sqrt" -> sqrt(arguments);
            case "calc" -> calc(arguments);
            case "date" -> date(arguments);
            default -> throw new IllegalArgumentException("Unknown command.");
        };
    }

    /**
     * Takes two positional arguments:
     *  - {@code left: <your integer type>}
     *  - {@code right: <your integer type>}
     */
    private static Map<String, Object> add(String arguments) throws ArgumentParserException
    {
        //TODO: Parse arguments and extract values.

        // Build the structure of a command by providing the ArgumentType of a command.
        var command = new Command(List.of(
                new Argument("left", ArgumentType.INTEGER, false, false),
                new Argument("right", ArgumentType.INTEGER, false,false)
        ));

        // parsedArguments behaves like a map. Notice that the values of the arguments are strings

            var parsedArguments  = command.parse(arguments);
            int left = Integer.parseInt(parsedArguments.get("left"));
            int right = Integer.parseInt(parsedArguments.get("right"));

            return Map.of("left", left, "right", right);
    }

    /**
     * Takes two <em>named</em> arguments:
     *  - {@code left: <your decimal type>} (optional)
     *     - If your project supports default arguments, you could also parse
     *       this as a non-optional decimal value using a default of 0.0.
     *  - {@code right: <your decimal type>} (required)
     */
    static Map<String, Object> sub(String arguments) throws ArgumentParserException{
        //TODO: Parse arguments and extract values.

        var command = new Command(List.of(
                new Argument("--left=", ArgumentType.DECIMAL, true, true),
                new Argument("--right=", ArgumentType.DECIMAL, false, true)
        ));

        // parsedArguments behaves like a map. Notice that the values of the arguments are strings
        var parsedArguments  = command.parse(arguments);

        //Optional<Double> left = Optional.empty();
       // double right = 0.0;

        Optional<Double> left = Optional.empty();
        double right = Double.parseDouble(parsedArguments.get("--right="));

        String leftValue = parsedArguments.get("--left=");

        if (!leftValue.equals("null")) {
            double parsedLeft = Double.parseDouble(leftValue);
            left = Optional.of(parsedLeft);
        }


        if (left.isEmpty())
        {
            return Map.of("left",Optional.empty() , "right", right);

        }
        else
        {
            return Map.of("left", left.get(), "right", right);

        }
    }

    /**
     * Takes one positional argument:
     *  - {@code number: <your integer type>} where {@code number >= 0}
     */
    static Map<String, Object> sqrt(String arguments) throws ArgumentParserException{
        //TODO: Parse arguments and extract values.
        var command = new Command(List.of(new Argument("number", ArgumentType.UNSIGNEDINT)));
        var passedArg = command.parse(arguments);
        int number = Integer.parseInt(passedArg.get("number"));
        return Map.of("number",number);
    }

    /**
     * Takes one positional argument:
     *  - {@code subcommand: "add" | "div" | "sqrt" }, aka one of these values.
     *     - Note: Not all projects support subcommands, but if yours does you
     *       may want to take advantage of this scenario for that.
     */
    static Map<String, Object> calc(String arguments) {
        //TODO: Parse arguments and extract values.
        String subcommand = "";
        return Map.of("subcommand", subcommand);
    }

    /**
     * Takes one positional argument:
     *  - {@code date: Date}, a custom type representing a {@code LocalDate}
     *    object (say at least yyyy-mm-dd, or whatever you prefer).
     *     - Note: Consider this a type that CANNOT be supported by your library
     *       out of the box and requires a custom type to be defined.
     */
    static Map<String, Object> date(String arguments) throws ArgumentParserException {
        //TODO: Parse arguments and extract values.
        var command = new Command(List.of(new Argument("date",ArgumentType.DATE)));
        var dateArg = command.parse(arguments);
        LocalDate date = LocalDate.parse(dateArg.get("date"));
        return Map.of("date", date);
    }

    //TODO: Add your own scenarios based on your software design writeup. You
    //should have a couple from pain points at least, and likely some others
    //for notable features. This doesn't need to be exhaustive, but this is a
    //good place to test/showcase your functionality in context.

}
