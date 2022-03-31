package com.bhma.client.utility;

import java.io.IOException;
import java.io.OutputStream;

/**
 * responsible for output
 */
public class OutputManager {
    private final OutputStream outputStream;
    private MessageNotifications messageNotifications = MessageNotifications.ON;

    private enum MessageNotifications {
        ON,
        OFF
    }

    public OutputManager(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * writes a string with a new string symbol in the end to the output stream that set in the constructor if notification is on
     * @param string
     */
    public void println(String string) {
        if (messageNotifications.equals(MessageNotifications.ON)) {
            printlnImportantMessage(string);
        }
    }

    public void printlnColorMessage(String string, Color color) {
        if (messageNotifications.equals(MessageNotifications.ON)) {
            printlnImportantColorMessage(string, color);
        }
    }

    public void printlnImportantColorMessage(String string, Color color) {
        printlnImportantMessage(color.toString() + string + "\u001B[0m");
    }

    /**
     * writes a string with a new string symbol in the end to the output stream that set in the constructor even if notification is off
     * @param string
     */
    public void printlnImportantMessage(String string) {
        try {
            outputStream.write(string.getBytes());
            outputStream.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes a string to the output stream that set in the constructor if notification is on
     * @param string
     */
    public void print(String string) {
        try {
            if (messageNotifications.equals(MessageNotifications.ON)) {
                outputStream.write(string.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * turn off notification; messages will be not written until you turn on notification
     */
    public void muteNotifications() {
        messageNotifications = MessageNotifications.OFF;
    }

    /**
     * turn on notification
     */
    public void enableNotifications() {
        messageNotifications = MessageNotifications.ON;
    }
}
