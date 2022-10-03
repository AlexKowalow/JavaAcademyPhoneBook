package org.example.phonebook;

import org.apache.logging.log4j.Logger;
import org.example.contact.Contact;
import org.example.exception.ElementAlreadyExistsException;
import org.example.repository.ContactRepository;

import java.io.*;

public class PhoneBook {
    private ContactRepository contactRepository;
    private Logger logger;

    public PhoneBook(ContactRepository contactRepository, Logger logger) {
        this.contactRepository = contactRepository;
        this.logger = logger;
    }

    public void writeDataToFile(String filename) {
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))
        ) {
            var contacts = contactRepository.getContacts();

            for (var c : contacts) {
                bw.write(c.toString() + "\n");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void loadDataFromFile(String filename) {
        try (
                BufferedReader br = new BufferedReader(new FileReader(filename))
        ) {
            while (true) {
                var line = br.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }

                var data = line.split(",");
                Contact contact = new Contact(data[0], data[1]);
                contactRepository.addContact(contact);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Contact addContact(Contact contact) throws ElementAlreadyExistsException {
        return contactRepository.addContact(contact);
    }

    public Contact removeContact(Contact contact) {
        return contactRepository.removeContact(contact);
    }

    public Contact removeContact(String name) {
        return contactRepository.removeContact(name);
    }

    public Contact findByName(String name) {
        return contactRepository.findByName(name);
    }

    public Contact findByNumber(String number) {
        return contactRepository.findByNumber(number);
    }
}
