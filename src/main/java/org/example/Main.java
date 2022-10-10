package org.example;

import org.apache.logging.log4j.LogManager;
import org.example.contact.Contact;
import org.example.phonebook.PhoneBook;
import org.example.repository.ContactRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /*
    W ramach zadania stwórz mini projekt, który będzie umożliwiał zarządzanie książką telefoniczną zapisywaną w pliku tekstowym.

W aplikacji będą zaimplementowane następujące funkcjonalności:

dodawanie nowych rekordów,
wyszukiwania po części nazwy,
wyszukiwania po części numeru telefonu,
usuwanie rekordów.
Program powinien zapisywać stan przy zamykaniu aplikacji.

Ograniczenia:

nazwy kontaktów w programie powinny być unikalne,
nazwa ani numer telefonu nie mogą być nullami ani pustymi Stringami ("").
     */
    public static void main(String[] args) {
        var sc = new Scanner(System.in);
        var logger = LogManager.getLogger();
        var contacts = new ArrayList<Contact>();
        var contactRepository = new ContactRepository(contacts);
        var phoneBook = new PhoneBook(contactRepository, logger);

        var filename = "db/contacts.csv";
        phoneBook.loadDataFromFile(filename);

        try {
            while (true) {
                System.out.println("================");
                System.out.println(" SELECT OPTION  ");
                System.out.println("================");
                System.out.println("Add contact    1");
                System.out.println("Find by name   2");
                System.out.println("Find by number 3");
                System.out.println("Remove contact 4");
                System.out.println("Get report     5");
                System.out.println("Exit           6");
                System.out.println("================");
                System.out.print("\t> ");

                boolean exit = false;
                int option = Integer.parseInt(sc.nextLine());

                switch (option) {
                    case 1 -> {
                        System.out.println("Add contact");

                        System.out.print("Enter contact name: ");
                        var name = sc.nextLine();

                        System.out.print("Enter number: ");
                        var number = sc.nextLine();

                        var contact = new Contact(name, number);
                        contact = phoneBook.addContact(contact);
                        logger.debug("Contact added: " + contact);
                    }
                    case 2 -> {
                        System.out.println("Find by name");
                        System.out.print("Enter contact name: ");
                        var name = sc.nextLine();
                        var contact = phoneBook.findByName(name);

                        if (contact == null) {
                            logger.debug("No contacts found by name [ " + name + " ]");
                        }
                        else {
                            logger.debug("Contact found by name [ " + name + " ] : " + contact);
                        }
                    }
                    case 3 -> {
                        System.out.println("Find by number");
                        System.out.print("Enter contact number: ");
                        var number = sc.nextLine();
                        var contact = phoneBook.findByNumber(number);

                        if (contact == null) {
                            logger.debug("No contacts found by number [ " + number + " ]");
                        }
                        else {
                            logger.debug("Contact found by number [ " + number + " ] : " + contact);
                        }
                    }
                    case 4 -> {
                        System.out.println("Remove contact");
                        System.out.print("Enter contact name: ");
                        var name = sc.nextLine();
                        var contact = phoneBook.removeContact(name);

                        if (contact == null) {
                            logger.debug("No contacts removed by name [ " + name + " ]");
                        }
                        else {
                            logger.debug("Contact removed by name [ " + name + " ] : " + contact);
                        }
                    }
                    case 5 -> {
                        System.out.print("Enter email to send report: ");
                        String email = sc.nextLine();

                        logger.debug("Sending report to email [ " + email + " ]...");

                        phoneBook.sendReport(email);

                        logger.debug("Sending report to email [ " + email + " ] - DONE");
                    }
                    case 6 -> {
                        logger.debug("Writing data to file [ " + filename + " ]...");
                        phoneBook.writeDataToFile(filename);
                        logger.debug("Writing data to file [ " + filename + " ] - DONE");
                        logger.debug("EXIT");
                        exit = true;
                    }
                    default -> {
                        throw new Exception("Unknown option");
                    }
                }

                if (exit) {
                    break;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            phoneBook.writeDataToFile(filename);
        }
    }
}