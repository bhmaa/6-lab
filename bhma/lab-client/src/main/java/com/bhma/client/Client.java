package com.bhma.client;

import java.util.StringJoiner;

import javax.xml.bind.JAXBException;

import com.bhma.client.exceptions.InvalidInputException;
import com.bhma.client.utility.CollectionCreator;
import com.bhma.client.utility.CollectionManager;
import com.bhma.client.utility.Color;
import com.bhma.client.utility.CommandManager;
import com.bhma.client.utility.ConsoleManager;
import com.bhma.client.utility.InputManager;
import com.bhma.client.utility.OutputManager;
import com.bhma.client.utility.SpaceMarineFiller;
import com.bhma.client.utility.SpaceMarineReader;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        OutputManager outputManager = new OutputManager(System.out);
        InputManager inputManager = new InputManager(System.in, outputManager);
        if (args.length == 0) {
            outputManager.printlnImportantColorMessage("Please enter the file path as a command line argument",
                    Color.RED);
        } else {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (String arg : args) {
                stringJoiner.add(arg);
            }
            String filename = stringJoiner.toString();
            try {
                CollectionManager collectionManager = CollectionCreator.load(filename, outputManager);
                SpaceMarineFiller spaceMarineFiller = new SpaceMarineFiller(new SpaceMarineReader(inputManager),
                        inputManager, outputManager);
                CommandManager commandManager = new CommandManager(collectionManager, spaceMarineFiller, inputManager, outputManager);
                ConsoleManager consoleManager = new ConsoleManager(commandManager, inputManager, outputManager);
                consoleManager.start();
            } catch (JAXBException e) {
                outputManager.printlnImportantColorMessage("Error during converting xml file "
                        + filename + " to java object.", Color.RED);
            } catch (InvalidInputException e) {
                outputManager.printlnImportantColorMessage(e.getMessage(), Color.RED);
            }

        }
    }
}
