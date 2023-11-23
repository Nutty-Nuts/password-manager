package utils;

import java.io.*;
import java.util.Scanner;

public class FileSystem {
    public FileSystem() {

    }

    public void readFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Contents of " + filename + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        System.out.println();
    }

    public String fetchFromFile(String filename) {
        String output = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output = output + line;
            }
            System.out.println("Fetched contents of " + filename);
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        return output;
    }

    public void writeToFile(String filename, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(data);
            System.out.println("Data successfully written to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void appendToFile(String filename, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(data);
            System.out.println("Data successfully appended to " + filename);
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }

    public void createFile(String filename) {
        if (new File(filename).exists()) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(filename)) {
            // You can add logic here to input and store passwords securely
            System.out.printf("File created: %s.txt\n", filename);
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }
}
