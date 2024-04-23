package oop.project.cli;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

    List<Argument> argumentsStructure;
    int numberOfOptionalArguments;
    int numberOfRequiredArguments;
    int totalNumberOfArguments;
    int numberOfNamedArguments;
    int numberOfNamedPositionalArguments;

    public Command(List<Argument> arguments)
    {
        this.argumentsStructure = arguments;
        this.totalNumberOfArguments = arguments.size();

        for(Argument arg : arguments) {if (arg.optional) numberOfOptionalArguments++;}
        for(Argument arg : arguments) {if (!arg.optional) numberOfRequiredArguments++;}
        for(Argument arg : arguments) {if (arg.named) numberOfNamedArguments++;}
        for(Argument arg : arguments) {if (!arg.named) numberOfNamedPositionalArguments++;}
    }

    Map<String, String> parse(String arguments) throws ArgumentParserException
    {
        Map<String, String> parsedArguments = new HashMap<String, String>();

        String[] words = arguments.split(" ");
        String[] wordsWithoutName = new String[totalNumberOfArguments];


        for (int i = 0; i < totalNumberOfArguments; i++) { wordsWithoutName[i] = "null";}

        for (int i = 0; i < words.length; i++)
        {
            if (argumentsStructure.get(i).named)
            {
                String[] parts = words[i].split("=");
                wordsWithoutName[i] = parts[1];
                continue;
            }
            wordsWithoutName[i] = words[i];
        }


        // Check for number of arguments
        if (words.length > totalNumberOfArguments)
        {
            throw new ArgumentParserException("Too many arguments provided");
        }
        else if (words.length < numberOfRequiredArguments)
        {
            throw new ArgumentParserException("Not enough arguments provided");
        }

        // Check for required args

        for (int i = 0 ; i < numberOfRequiredArguments; i++)
        {
            for (var arg : argumentsStructure)
            {
                if (arg.named)
                {
                    if (!arg.optional)
                    {
                        // Search for it in the words
                        boolean contains = false;

                        for (String word : words)
                        {
                            if (word.contains(arg.name))
                            {
                                contains = true;
                            }
                        }

                        if (!contains) throw new ArgumentParserException("Required arguments needed.");
                    }
                }


            }
        }


        // Parse each argument based on the structure
        for (int i = 0; i < words.length; i++)
        {


            if (argumentsStructure.get(i).argType == ArgumentType.INTEGER)
            {

                try
                {
                    Integer.valueOf(words[i]);
                }
                catch (NumberFormatException e)
                {
                    throw new ArgumentParserException(words[i] + " is not an integer.");
                }

            }
            else if (argumentsStructure.get(i).argType == ArgumentType.DECIMAL) {
                try
                {
                    Double.valueOf(wordsWithoutName[i]);
                }
                catch (NumberFormatException e)
                {
                    throw new ArgumentParserException(words[i] + " is not a double.");
                }
            }
        }



//        for (int i = 0; i < words.length; i++)
//        {
//            if (argumentsStructure.get(i).named)
//            {
//                String[] parts = words[i].split("=");
//                wordsWithoutName[i] = parts[1];
//                continue;
//            }
//            wordsWithoutName[i] = words[i];
//        }




        for (int i = 0; i < totalNumberOfArguments; i++)
        {
            String key = argumentsStructure.get(i).name;
            String value = "null";

            if (!argumentsStructure.get(i).named)
            {
                value = words[i];
            }
            else
            {
                for (String word : words)
                {
                    if (word.contains(key))
                    {
                        value = word.substring(word.indexOf('=') + 1);
                    }
                }
            }

            parsedArguments.put(key, value);


        }

        return parsedArguments;
    }

}
