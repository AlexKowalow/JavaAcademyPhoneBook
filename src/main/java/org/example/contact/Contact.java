package org.example.contact;

import org.example.exception.InvalidArgumentsProvidedException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Contact {
    private String name;
    private String number;

    public Contact(String name, String number) throws InvalidArgumentsProvidedException {
        if (name == null || number == null || name.isEmpty() || number.isEmpty() || !verifyNumber(number)) {
            throw new InvalidArgumentsProvidedException("Neither name nor number may be empty or nulls. Number must be polish e.g. +48 XXX XXX XXX");
        }
        this.name = name;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && Objects.equals(number, contact.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }

    @Override
    public String toString() {
        return name + "," + number;
    }

    private boolean verifyNumber(String number) {
        Pattern pattern = Pattern.compile("^\\+48 [0-9]{3} [0-9]{3} [0-9]{3}$");
        return pattern.matcher(number).find();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
