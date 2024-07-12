package controller;

import phone.Phone;

public interface PhoneOperations {
    void addPhone(Phone phone);
    void deletePhone(int id) throws NotFoundProductException;
    void viewPhones();
    void searchPhones(String keyword);
    void loadPhones();
    void savePhones();
}
