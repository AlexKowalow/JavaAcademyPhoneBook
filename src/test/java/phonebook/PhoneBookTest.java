package phonebook;

import org.apache.logging.log4j.LogManager;
import org.example.contact.Contact;
import org.example.exception.ElementAlreadyExistsException;
import org.example.exception.InvalidArgumentsProvidedException;
import org.example.phonebook.PhoneBook;
import org.example.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
public class PhoneBookTest {
    private PhoneBook phoneBook;

    @BeforeEach
    public void initPhoneBook() {
        var contacts = new ArrayList<Contact>();
        var contactRepository = new ContactRepository(contacts);

        var logger = LogManager.getLogger();

        phoneBook = new PhoneBook(contactRepository, logger);
    }

    @Test
    public void testFindByExistingNumber() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        phoneBook.addContact(new Contact("aaaa", "+48 111 222 333"));
        phoneBook.addContact(new Contact("bbbb", "+48 111 222 444"));
        phoneBook.addContact(new Contact("cccc", "+48 111 222 555"));

        Contact contact;
        contact = phoneBook.findByNumber("+48 111 222 333");
        assertEquals(contact.getName(), "aaaa");
    }

    @Test
    public void testFindByNonExistingNumber() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        phoneBook.addContact(new Contact("aaaa", "+48 111 222 333"));
        phoneBook.addContact(new Contact("bbbb", "+48 111 222 444"));
        phoneBook.addContact(new Contact("cccc", "+48 111 222 555"));

        var exception = assertThrows(NoSuchElementException.class, () -> phoneBook.findByNumber("+1222333999"));
    }

    @Test
    public void testFindByExistingName() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        var c1 = new Contact("aaaa", "+48 111 222 333");
        var c2 = new Contact("bbbb", "+48 111 222 444");
        var c3 = new Contact("cccc", "+48 111 222 555");


        phoneBook.addContact(c1);
        phoneBook.addContact(c2);
        phoneBook.addContact(c3);

        Contact contact;
        contact = phoneBook.findByName("aaaa");
        assertEquals(contact, c1);
    }

    @Test
    public void testFindByNonExistingName() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        phoneBook.addContact(new Contact("aaaa", "+48 111 222 333"));
        phoneBook.addContact(new Contact("bbbb", "+48 111 222 444"));
        phoneBook.addContact(new Contact("cccc", "+48 111 222 555"));

        var exception = assertThrows(NoSuchElementException.class, () -> phoneBook.findByName("dddd"));
    }

    @Test
    public void testAddContact() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        var contact = new Contact("aaaa", "+48 111 222 333");

        var c = phoneBook.addContact(contact);
        assertEquals(contact, c);
    }

    @Test
    public void testAddContactDuplicate() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        var contact = new Contact("aaaa", "+48 111 222 333");
        var c = phoneBook.addContact(contact);
        var exception = assertThrows(ElementAlreadyExistsException.class, () ->phoneBook.addContact(contact));
    }

    @Test
    public void testRemoveContact() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        var c1 = new Contact("aaaa", "+48 111 222 333");
        var c2 = new Contact("bbbb", "+48 111 222 444");
        var c3 = new Contact("cccc", "+48 111 222 555");

        phoneBook.addContact(c1);
        phoneBook.addContact(c2);
        phoneBook.addContact(c3);

        var removed = phoneBook.removeContact(c1);
        assertEquals(c1, removed);
    }

    @Test
    public void testRemoveNonExistingContact() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        var c1 = new Contact("aaaa", "+48 111 222 333");
        var c2 = new Contact("bbbb", "+48 111 222 444");
        var c3 = new Contact("cccc", "+48 111 222 555");
        var c4 = new Contact("dddd", "+48 111 222 666");

        phoneBook.addContact(c1);
        phoneBook.addContact(c2);
        phoneBook.addContact(c3);

        var removed = phoneBook.removeContact(c4);
        assertNull(removed);
    }

    @Test
    public void testRemoveContactByName() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        var c1 = new Contact("aaaa", "+48 111 222 333");
        var c2 = new Contact("bbbb", "+48 111 222 444");
        var c3 = new Contact("cccc", "+48 111 222 555");

        phoneBook.addContact(c1);
        phoneBook.addContact(c2);
        phoneBook.addContact(c3);

        var removed = phoneBook.removeContact(c1.getName());
        assertEquals(c1, removed);
    }

    @Test
    public void testRemoveNonExistingContactByName() throws InvalidArgumentsProvidedException, ElementAlreadyExistsException {
        var c1 = new Contact("aaaa", "+48 111 222 333");
        var c2 = new Contact("bbbb", "+48 111 222 444");
        var c3 = new Contact("cccc", "+48 111 222 555");
        var c4 = new Contact("dddd", "+48 111 222 666");

        phoneBook.addContact(c1);
        phoneBook.addContact(c2);
        phoneBook.addContact(c3);

        var removed = phoneBook.removeContact(c4.getName());
        assertNull(removed);
    }


    @Test
    public void createContact() throws InvalidArgumentsProvidedException {
        var c1 = new Contact("aaaa", "+48 000 000 000");
        assertNotNull(c1);
    }

    @Test
    public void testCreateContactInvalidNumber() {
        assertThrows(InvalidArgumentsProvidedException.class, () -> new Contact("aaaa", "48 000 000 000"));
    }

    @Test
    public void testCreateContactNullNumber() {
        assertThrows(InvalidArgumentsProvidedException.class, () -> new Contact("aaaa", null));
    }

    @Test
    public void testCreateContactEmptyNumber() {
        assertThrows(InvalidArgumentsProvidedException.class, () -> new Contact("aaaa", ""));
    }

    @Test
    public void testCreateContactNullName() {
        assertThrows(InvalidArgumentsProvidedException.class, () -> new Contact( null, "+48 000 000 000"));
    }

    @Test
    public void testCreateContactEmptyName() {
        assertThrows(InvalidArgumentsProvidedException.class, () -> new Contact("", "+48 000 000 000"));
    }
}
