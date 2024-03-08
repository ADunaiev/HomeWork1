package step.learning;

import javafx.scene.SubScene;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PhoneBook {
    public void start(){
        Map<String, String> contacts = new HashMap<String, String>();
        //contacts.put("Andrii", "+38 050 123 45 67");
        //contacts.put("Maxim Petrenko", "+38 050 123 44 55");
        //contacts.put("Maxim", "123");
        //contacts.put("Pavlo", "+38 050 123 66 77");
        //contacts.put("Tetiana", "123");
        contacts = loadFromFile();
        //saveMapToFile(contacts);

        while(true) {
            int choice = showMainMenu();

            switch (choice) {
                case 1:
                    showAllContacts(contacts);
                    break;
                case 2:
                    searchByNumber(contacts);
                    break;
                case 3:
                    searchByName(contacts);
                    break;
                case 4:
                    addNewContact(contacts);
                    break;
                case 0:
                    saveMapToFile(contacts);
                    System.exit(0);
                default:
                    System.out.println("Looking for your choice");
            }
        }

    }

    protected void searchByNumber(Map<String, String> contacts) {
        System.out.flush();

        Scanner kbScanner = new Scanner(System.in);
        System.out.print("\nPlease enter number: ");
        String search_number = kbScanner.next();

        boolean result;
        result = false;

        System.out.println("\nContact is found:");

        for (Map.Entry<String, String> entry: contacts.entrySet()) {


            if (entry.getValue().contains(search_number)) {
                System.out.printf (
                        "%s %s\n",
                        entry.getKey(),
                        entry.getValue()
                );
                result = true;
            }
        }

        if (!result) {
            System.out.println("Nothing was found");
        }
    }
    protected void searchByName(Map<String, String> contacts) {

        Scanner kbScanner = new Scanner(System.in);
        System.out.print("\nPlease enter name: ");
        String search_name = kbScanner.next();

        boolean result;
        result = false;

        System.out.println("\nContact is found:");

        for (Map.Entry<String, String> entry: contacts.entrySet()) {


            if (entry.getKey().contains(search_name)) {
                System.out.printf (
                        "%s %s\n",
                        entry.getKey(),
                        entry.getValue()
                );
                result = true;
            }
        }

        if (!result) {
            System.out.println("Nothing was found");
        }
    }
    protected void addNewContact(Map<String, String> contacts) {

        boolean check;
        Scanner kbScanner = new Scanner(System.in);
        String new_name = "";

        do {
            System.out.print("\nPlease enter name: ");
            new_name = kbScanner.next();

            check = true;

            for (Map.Entry<String, String> entry: contacts.entrySet()) {
                if(entry.getKey().equals((new_name))) {
                    System.out.println("There is such name already!");
                    check = false;
                    break;
                }
            }

        } while(!check);

        System.out.print("\nPlease enter phone number: ");
        String new_number = kbScanner.next();

        contacts.put(new_name, new_number);
        System.out.println("New contact is added successfully!");

    }
    protected void showAllContacts(Map<String, String> contacts) {
        System.out.print("\nContacts:\n");
        for(Map.Entry<String, String> entry: contacts.entrySet()) {
            System.out.printf(
                    "%s => %s,\n",
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }
    protected int showMainMenu() {
        System.out.println("\nYour Phonebook app\nPlease choose your action:");
        System.out.println("1 - show all contacts");
        System.out.println("2 - search by phone number");
        System.out.println("3 - search by name");
        System.out.println("4 - add new contact");
        System.out.println("0 - exit");

        Scanner kbScanner = new Scanner(System.in);
        System.out.print("\nMake your choice: ");

        return kbScanner.nextInt();
    }

    protected void saveMapToFile(Map<String, String> contacts) {
        File file = new File("contacts");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.writeObject(contacts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            oos.flush();
            oos.close();;
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected HashMap<String, String> loadFromFile() {
        HashMap<String, String> result;


        FileInputStream fis = null;
        try {
            fis = new FileInputStream("contacts");
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (HashMap<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return result;
    }
}
