// CSE 360 Project - Team 10
// Noah Anderson, Adam Harvey, Adwith Malpe, Ryan Schmidt
// Utilities for saving and loading lists from files
package todo;

import java.io.*;

/**
 * Provides utilities for saving and loading lists from files
 */
public class FileManager {
    /**
     * Loads a list from the specified file.
     *
     * @param file File to load list from
     * @return The list present in the file
     * @throws Exception If the file doesn't contain a valid list or we had an error opening it
     */
    public static ITodoList loadFromFile(File file) throws Exception {

        try (FileInputStream inFile = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(inFile)) {
            return (ITodoList)in.readObject();
        }
    }

    /**
     * Saves a list to the specified file.
     *
     * @param file File to save list to; will be created if it doesn't already exist and overwritten otherwise
     * @param list List data to save
     * @throws Exception If we have an issue saving the file (for example, permissions)
     */
    public static void saveToFile(File file, ITodoList list) throws Exception {
        try (FileOutputStream outFile = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(outFile)) {
            out.writeObject(list);
        }
    }
}
