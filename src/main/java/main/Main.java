package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import algorithms.BinarySearch;
import algorithms.Crypto;
import algorithms.FindBounds;
import algorithms.RadixSort;
import structures.Entry;
import structures.HashMap;

import utils.Constants.PATH;
import utils.Constants;
import utils.FileSystem;
import utils.Formatter;
import utils.Helpers;
import utils.Logger;

public class Main {
    private static HashMap<String, String> storage;
    private static FileSystem fs;
    private static Logger logger;
    private static Scanner scanner;

    private static Entry[] entries;
    private static int size = 0;
    private static boolean isInit = false, isEmpty = false;

    private static void initClasses() {
        fs = new FileSystem();
        logger = new Logger(false);
        scanner = new Scanner(System.in);

        storage = new HashMap<String, String>(16);
        entries = new Entry[16];
    }

    private static void initApp() {
        boolean isPassEmpty = false, isServEmpty = false, isAccEmpty = false;

        fs.createFile(PATH.PASSWORDS);
        fs.createFile(PATH.SERVICES);
        fs.createFile(PATH.ACCOUNTS);

        String raw;
        String[] filePass, fileServ, fileAcc;

        raw = fs.fetchFromFile(PATH.SERVICES);
        fileServ = raw.split("\n");
        if (fileServ[0].equals("")) {
            logger.log("there are no services in storage");
            isServEmpty = true;
        }

        raw = fs.fetchFromFile(PATH.ACCOUNTS);
        fileAcc = raw.split("\n");
        if (fileAcc[0].equals("")) {
            logger.log("there are no accounts in storage");
            isAccEmpty = true;
        }

        raw = fs.fetchFromFile(PATH.PASSWORDS);
        filePass = raw.split("\n");
        if (filePass[0].equals("")) {
            logger.log("there are no passwords in storage");
            isPassEmpty = true;
        }

        if (isPassEmpty || isServEmpty || isAccEmpty) {
            System.out.println("There are no entries found in storage");
            isInit = true;
            isEmpty = true;
            return;
        }

        for (int i = 0; i < filePass.length; i++) {
            add(Crypto.decrypt(fileServ[i]), Crypto.decrypt(fileAcc[i]), Crypto.decrypt(filePass[i]));
        }

        isInit = true;
    }

    private static void add(String service, String account, String password) {
        checkLoad();

        Entry entry = new Entry(service, account);

        // prevent increment when entry already exists
        if (storage.get(entry.key()) != null) {
            storage.put(entry.key(), password);
            rewrite();
            System.out.println();
            System.out.println("Password has been updated");
            return;
        }
        // prevent writing to storage when uninitialized
        if (isInit) {
            Helpers.STORAGE.addToStorage(service, account, password);
            System.out.println();
            System.out.println("New password added to storage");
        }
        entries[size] = entry;
        storage.put(entries[size].key(), password);

        isEmpty = false;
        size++;
    }

    private static void remove(String key) {
        RadixSort.sort(entries, 2);

        int index = BinarySearch.binarySearch(entries, key, size);
        if (index == -1) {
            System.out.println();
            System.out.println("The password entry cannot be removed because it does not exist");
            return;
        }

        storage.remove(key);
        shift(index);

        rewrite();
        System.out.println();
        System.out.println("The password entry has been removed");
    }

    private static void rewrite() {
        if (size == 0) {
            Helpers.STORAGE.purgeStorage();
            return;
        }
        Helpers.STORAGE.overiteStorage(entries[0].service, entries[0].account, storage.get(entries[0].key()));
        for (int i = 1; i < size; i++)
            Helpers.STORAGE.addToStorage(entries[i].service, entries[i].account, storage.get(entries[i].key()));
    }

    private static void shift(int index) {
        entries[index] = null;

        for (int i = index; i < size; i++)
            entries[i] = (i == size - 1) ? null : entries[i + 1];

        size--;
    }

    private static void resize() {
        Entry[] newEntries = new Entry[entries.length * 2];

        for (int i = 0; i < entries.length; i++)
            newEntries[i] = entries[i];

        entries = newEntries;
    }

    private static void checkLoad() {
        double load = (double) size / (double) entries.length;

        if (load >= Constants.LOAD_FACTOR)
            resize();
    }

    private static void searchByService(String pattern) {
        RadixSort.sort(entries, 0);
        String[] arr = new String[size];

        for (int i = 0; i < size; i++)
            arr[i] = entries[i].service;

        int lower = FindBounds.lowerBounds(arr, pattern, size);
        int upper = FindBounds.upperBounds(arr, pattern, size);

        if (lower == -1 || upper == -1) {
            System.out.printf("The word \"%s\" was not found in the list of services \n", pattern);
            return;
        }

        System.out.printf("Here are the following services that begins with \"%s\":\n", pattern);
        Formatter.print(entries, storage, lower, upper, (upper - lower + 1));
    }

    private static void searchByAccount(String pattern) {
        RadixSort.sort(entries, 1);
        String[] arr = new String[size];

        for (int i = 0; i < size; i++)
            arr[i] = entries[i].account;

        int lower = FindBounds.lowerBounds(arr, pattern, size);
        int upper = FindBounds.upperBounds(arr, pattern, size);

        if (lower == -1 || upper == -1) {
            System.out.printf("The word \"%s\" was not found in the list of services \n", pattern);
            return;
        }

        System.out.printf("Here are the following accounts that begins with \"%s\":\n", pattern);
        Formatter.print(entries, storage, lower, upper, (upper - lower + 1));
    }

    private static void displayAll() {
        RadixSort.sort(entries, 0);

        System.out.println("These are all the stored passwords:");
        Formatter.print(entries, storage, 0, size - 1, size);
    }

    private static boolean checkEmpty() {
        if (isEmpty) {
            System.out.println("There are no passwords entries in storage");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        initClasses();
        initApp();

        System.out.println("[0]: display all passwords               [4]: edit an existing password");
        System.out.println("[1]: search passwords by service name    [5]: delete an existing password");
        System.out.println("[2]: search passwords by account name    [6]: delete all passwords");
        System.out.println("[3]: add a new password");
        System.out.println();
        int choice = 0;
        System.out.print("Choose an operation: ");
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid input");
            System.exit(0);
        }
        System.out.println();

        switch (choice) {
            // display all passwords
            case 0 -> {
                if (checkEmpty()) {
                    break;
                }
                displayAll();
            }
            // search by service name
            case 1 -> {
                if (checkEmpty()) {
                    break;
                }
                System.out.print("Enter a keyword to search for a service: ");
                searchByService(scanner.next());
            }
            // search by account name
            case 2 -> {
                if (checkEmpty()) {
                    break;
                }
                System.out.print("Enter a keyword to search for an account: ");
                searchByAccount(scanner.next());
            }
            // add an entry
            case 3 -> {
                System.out.print("Enter the name of the service: ");
                String service = scanner.next();
                System.out.print("Enter the name of the account: ");
                String account = scanner.next();

                if (storage.get(service + account) != null) {
                    System.out.println("This account already exists");
                    break;
                }

                System.out.print("Enter the password: ");
                String password = scanner.next();

                add(service, account, password);
            }
            // edit an entry
            case 4 -> {
                if (checkEmpty()) {
                    break;
                }
                System.out.print("Enter the name of the service: ");
                String service = scanner.next();
                System.out.print("Enter the name of the account: ");
                String account = scanner.next();
                System.out.print("Enter the new password: ");
                String password = scanner.next();

                if (storage.get(service + account) == null) {
                    System.out.println("This account does not exist");
                    break;
                }

                add(service, account, password);
            }
            case 5 -> {
                if (checkEmpty()) {
                    break;
                }
                System.out.print("Enter the name of the service: ");
                String service = scanner.next();
                System.out.print("Enter the name of the account: ");
                String account = scanner.next();
                String key = service + account;

                remove(key);
            }
            case 6 -> {
                Helpers.STORAGE.purgeStorage();
                System.out.println("Removed all password entries from storage");
            }
            default -> {
                System.out.println("Please enter a valid operation");
            }
        }
    }
}