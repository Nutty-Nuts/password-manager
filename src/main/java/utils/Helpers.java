package utils;

import java.io.File;

import utils.Constants.PATH;
import algorithms.Crypto;

public class Helpers {
    private static FileSystem fs = new FileSystem();

    public static class STORAGE {
        public static void addToStorage(String service, String account, String password) {
            fs.appendToFile(PATH.SERVICES, Crypto.encrypt(service) + "\n");
            fs.appendToFile(PATH.ACCOUNTS, Crypto.encrypt(account) + "\n");
            fs.appendToFile(PATH.PASSWORDS, Crypto.encrypt(password) + "\n");
        }

        public static void overiteStorage(String service, String account, String password) {
            fs.writeToFile(PATH.SERVICES, Crypto.encrypt(service) + "\n");
            fs.writeToFile(PATH.ACCOUNTS, Crypto.encrypt(account) + "\n");
            fs.writeToFile(PATH.PASSWORDS, Crypto.encrypt(password) + "\n");
        }

        public static void purgeStorage() {
            fs.deleteFile(PATH.SERVICES);
            fs.deleteFile(PATH.ACCOUNTS);
            fs.deleteFile(PATH.PASSWORDS);
        }
    }
}
