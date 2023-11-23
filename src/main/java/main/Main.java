package main;

import java.util.Scanner;

import algorithms.RadixSort;
import algorithms.Crypto;
import algorithms.BinarySearch;
import algorithms.FindBounds;

import structures.HashMap;

import utils.FileSystem;
import utils.Constants;
import utils.Constants.PATH;

public class Main {
    private static String[] services;
    private static HashMap storage;
    private static FileSystem fs;

    private static int servicesStored = 0, servicesEmpty = 0;
    private static boolean isInitialized = false;

    private static void initialize() {
        storage = new HashMap(4);
        services = new String[4];
        fs = new FileSystem();

        // fs.createFile(PATH.PASSWORDS);

        String raw = fs.fetchFromFile(PATH.PASSWORDS);
        String[] data = raw.split(",");

        for (String datum : data) {
            String[] pairs = datum.split(":");
            add(pairs[0], Crypto.decrypt(pairs[1]));
        }

        isInitialized = true;
    }

    public static void add(String key, String value) {
        double servicesLoad = (double) servicesStored / (double) services.length;

        if (servicesLoad >= Constants.LOAD_FACTOR) {
            resize();
        }

        if (storage.get(key) != null) {
            storage.put(key, value);
            return;
        }

        if (!isInitialized) {
            services[servicesEmpty] = key;
            RadixSort.radixSort(services);

            storage.put(key, value);

            servicesStored++;
            servicesEmpty++;

            return;
        }

        services[servicesEmpty] = key;
        RadixSort.radixSort(services);

        storage.put(key, value);

        servicesStored++;
        servicesEmpty++;

        fs.appendToFile(PATH.PASSWORDS, key + ":" + Crypto.encrypt(value) + ",");
    }

    private static void remove(String string) {
        int index = BinarySearch.binarySearch(services, string, servicesStored);
        String key = services[index];

        System.out.println(storage.get(key));
        storage.remove(key);
        shift(index);

        servicesStored--;
        servicesEmpty--;

        fs.writeToFile(PATH.PASSWORDS, services[0] + ":" + Crypto.encrypt(storage.get(services[0])) + ",");
        for (String service : services) {
            if (service == null) {
                break;
            }
            fs.appendToFile(PATH.PASSWORDS, service + ":" + Crypto.encrypt(storage.get(service)) + ",");
        }
    }

    private static void shift(int index) {
        services[index] = null;

        for (int i = index; i < servicesStored; i++) {
            if (i == servicesStored - 1) {
                services[i] = null;
            }
            services[i] = services[i + 1];
        }
    }

    private static void resize() {
        String[] temp = services;
        services = new String[temp.length * 2];

        for (int i = 0; i < temp.length; i++) {
            services[i] = temp[i];
        }
    }

    private static void displayServices() {
        for (String string : services) {
            if (string == null) {
                break;
            }
            System.out.print(string + ", ");
        }
        System.out.println();
    }

    private static void searchByKeyword(String keyword) {
        int lower = FindBounds.lowerBounds(services, keyword, servicesStored);
        int upper = FindBounds.upperBounds(services, keyword, servicesStored);

        for (int i = lower; i <= upper; i++) {
            System.out.println(services[i] + " " + storage.get(services[i]));
        }
    }

    private static void displayAllPasswords() {
        for (String string : services) {
            if (string == null) {
                break;
            }
            System.out.println(string + " " + storage.get(string));
        }
    }

    public static void main(String[] args) {
        initialize();

        Scanner sc = new Scanner(System.in);

        System.out.print("Choose an operation: ");
        int choice = sc.nextInt();

        switch (choice) {
            // search password by keyword
            case 0 -> {
                System.out.print("Enter a keyword: ");
                searchByKeyword(sc.next());
            }
            // list all passwords
            case 1 -> {
                displayAllPasswords();
            }
            // add a new password
            case 2 -> {
                System.out.print("Enter a service name: ");
                String service = sc.next();

                System.out.print("Enter a password: ");
                String password = sc.next();

                add(service, password);
            }
            case 3 -> {
                System.out.println("Enter a service name: ");
                String service = sc.next();
                remove(service);
            }
        }
    }
}
