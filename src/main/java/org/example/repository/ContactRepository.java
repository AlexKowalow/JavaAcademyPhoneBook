package org.example.repository;

import org.example.contact.Contact;
import org.example.exception.ElementAlreadyExistsException;

import java.util.List;

public class ContactRepository {
    private List<Contact> contacts;

    public ContactRepository(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Contact addContact(Contact contact) throws ElementAlreadyExistsException {
        var exists = contacts
                .stream()
                .anyMatch(c -> c.getName().equals(contact.getName()));

        if (exists) {
            throw new ElementAlreadyExistsException("Contact with this name already exists");
        }

        contacts.add(contact);
        return contact;
    }

    public Contact removeContact(Contact contact) {
        var removed = contacts.removeIf(c -> c.getName().equals(contact.getName()));
        if (removed) {
            return contact;
        }
        return null;
    }
    public Contact removeContact(String name) {
        var contact = contacts.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
        contacts.remove(contact);
        return contact;
    }

    public Contact findByName(String name) {
        return contacts
                .stream()
                .filter(c -> c.getName().contains(name))
                .findFirst()
                .orElseThrow();
    }

    public Contact findByNumber (String number) {
        return contacts
                .stream()
                .filter(c -> c.getNumber().contains(number))
                .findFirst()
                .orElseThrow();
    }
}
