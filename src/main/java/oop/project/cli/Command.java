package oop.project.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

    List<Argument> argumentsStructure;
    int numberOfArguments;

    public Command(List<Argument> arguments)
    {
        this.argumentsStructure = arguments;
        this.numberOfArguments = arguments.size();
    }

    Map<String, String> parse(String arguments) throws ArgumentParserException
    {
        Map<String, String> parsedArguments = new HashMap<String, String>();

        String[] words = arguments.split(" ");

        // Check for number of arguments
        if (words.length != numberOfArguments)
        {
            throw new ArgumentParserException(numberOfArguments + " arguments are required.");
        }

        // Parse each argument based on the structure
        for (int i = 0; i < numberOfArguments; i++)
        {
            if (argumentsStructure.get(i).argType == ArgumentType.INTEGER)
            {
                try
                {
                    Integer.valueOf(words[i]);
                }
                catch (NumberFormatException e)
                {
                    throw new ArgumentParserException(words[i] + " is not a number.");
                }

            }
            else if (argumentsStructure.get(i).argType == ArgumentType.DECIMAL) {
                // implement if we want to have decimals
            }
            else if(argumentsStructure.get(i).argType == ArgumentType.UNSIGNEDINT) {
                try {
                    if(Integer.valueOf(words[i]) < 0) {
                        throw new ArgumentParserException(words[i] + " is not a positive number.");
                    }
                }
                catch(NumberFormatException e) {
                    throw new ArgumentParserException(words[i] + " is not a number.");
                }
            }
        }

        // Put arguments in map
        for (int i = 0; i < numberOfArguments; i++)
        {
            parsedArguments.put(argumentsStructure.get(i).name, words[i]);
        }



        return parsedArguments;
    }

}
