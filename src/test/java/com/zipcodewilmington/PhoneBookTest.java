package com.zipcodewilmington;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PhoneBookTest{

    PhoneBook book;
    String num1;
    String num2;
    String name1;
    String name2;
    private static final Logger logger = LoggerFactory.getLogger(PhoneBook.class);

    @Before
    public void initialize() {
        book = new PhoneBook();
        num1 = "(302)731-1176";
        num2 = "(302)598-6245";
        name1 = "Kirby Kim";
        name2 = "Chris Kim";
    }


    public void lookupTest() throws RecordNotFoundException{
        String expected = "(302)731-1176";

        //When
        book.add(name1, num1);
        PhoneNumber firstNumber = book.lookup("Kirby Kim").get(0);
        String actual = firstNumber.toString();

        //Then
        assertEquals("Kirby Kim's phone number should correctly return (302)731-1176", expected, actual);
    }


    @Test (expected = RecordNotFoundException.class)
    public void lookupTestRecordNotFound() throws RecordNotFoundException{
        PhoneNumber firstNumber = book.lookup("Kirby Kim").get(0);
    }

    @Test
    public void addTestForSize(){
        //Given
        int expected = 1;

        //When
        book.add(name1, num1);
        int actual = book.size();

        //Then
        assertEquals("Size of Phonebook should be 1", expected, actual);
    }

    @Test
    public void addTestForEntry() throws RecordNotFoundException{
        //Given
        String expected = "(302)598-6245";

        //When
        book.add(name1, num1);
        book.add(name1, num2);

        PhoneNumber secondNumber = book.lookup("Kirby Kim").get(1);
        String actual = secondNumber.toString();

        //Then
        assertEquals("The second phone number for Kirby Kim should be (302)598-6245", expected, actual);
    }

    @Test
    public void removeTestForSameSize() throws RecordNotFoundException {
        //Given
        book.add(name1, num1);
        book.add(name2, num2);
        book.add(name2, num1);
        int expected = 2;

        //When
        book.remove("Chris Kim", "(302)731-1176");
        int actual = book.size();

        //Then
        assertEquals("Size of phonebook should still be 2, because Chris had two numbers", expected, actual);
    }

    @Test
    public void removeTestForDifferentSize() throws RecordNotFoundException {
        //Given
        book.add(name1, num1);
        book.add(name2, num2);
        int expected = 1;

        //When
        book.remove("Chris Kim", "(302)598-6245");
        int actual = book.size();

        //Then
        assertEquals("Size of phonebook should now be 1, because Chris had one number", expected, actual);
    }

    @Test (expected = RecordNotFoundException.class)
    public void removeTestThrowsRecordNotFoundException() throws RecordNotFoundException{
        book.remove("Nonexistent Record", "(555)555-5555");
    }

    @Test
    public void listNamesTest(){
        //Given
        book.add(name1, num1);
        book.add(name2, num2);
        String expected = "Chris Kim\nKirby Kim\n";

        //When
        String actual = book.listNames();

        //Then
        assertEquals("The return value should be \"Chris Kim\\nKirby Kim\n\"", expected, actual);
    }

    @Test
    public void listEntriesTest(){
        //Given
        book.add(name1, num1);
        book.add(name2, num2);
        book.add(name1, num2);
        book.add(name2, num1);
        String expected = "Chris Kim:\n  (302)598-6245\n  (302)731-1176\nKirby Kim:\n  (302)731-1176\n  (302)598-6245\n";

        //When
        String actual = book.listEntries();

        //Then
        assertEquals("The return value should be Chris Kim:\n  (302)598-6245\n  (302)731-1176\nKirby Kim:\n  (302)731-1176\n  (302)598-6245\n", expected, actual);
    }

    @Test
    public void reverseLookupTest() throws RecordNotFoundException, InvalidPhoneNumberFormatException {
        //Given
        book.add(name1, num1);
        book.add(name2, num2);
        String expected = "Chris Kim";

        //When
        String actual = book.reverseLookup("(302)598-6245");

        //Then
        assertEquals("The result of the lookup should be \"Chris Kim\"", expected, actual);
    }

    @Test (expected = RecordNotFoundException.class)
    public void reverseLookupRecordNotFoundTest() throws RecordNotFoundException, InvalidPhoneNumberFormatException {
        //When
        book.reverseLookup("(555)555-5555");
    }

    @Test (expected = InvalidPhoneNumberFormatException.class)
    public void reverseLookupInvalidPhoneNumberTest() throws RecordNotFoundException, InvalidPhoneNumberFormatException {
        //When
        book.reverseLookup("Phone Number");
    }
}
