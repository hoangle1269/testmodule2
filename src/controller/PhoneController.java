package controller;

import phone.Phone;
import phone.AuthenticPhone;
import phone.ImportPhone;
import phone.PhoneConstants;

import java.io.*;
import java.util.*;

public class PhoneController implements PhoneOperations {
    private static final String AUTHENTIC_FILE = "authentic_phones.csv";
    private static final String IMPORT_FILE = "authentic_phones.csv";
    private List<Phone> phones = new ArrayList<>();
    private int idCounter = 1;

    public PhoneController() {
        loadPhones();
    }

    @Override
    public void addPhone(Phone phone) {
        phones.add(phone);
    }

    @Override
    public void deletePhone(int id) throws NotFoundProductException {
        Phone phoneToDelete = findPhoneById(id);
        if (phoneToDelete != null) {
            phones.remove(phoneToDelete);
        } else {
            throw new NotFoundProductException("Phone does not exist");
        }
    }

    @Override
    public void viewPhones() {
        for (Phone phone : phones) {
            System.out.println(phone);
        }
    }

    @Override
    public void searchPhones(String keyword) {
        boolean found = false;
        for (Phone phone : phones) {
            if (String.valueOf(phone.getId()).toLowerCase().contains(keyword) || phone.getName().toLowerCase().contains(keyword)) {
                System.out.println(phone);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No phones found with the given keyword.");
        }
    }

    @Override
    public void loadPhones() {
        loadFromFile(PhoneConstants.AUTHENTIC_FILE, "authentic");
        loadFromFile(PhoneConstants.IMPORT_FILE, "import");
    }

    @Override
    public void savePhones() {
        saveToFile(PhoneConstants.AUTHENTIC_FILE, "authentic");
        saveToFile(PhoneConstants.IMPORT_FILE, "import");
    }

    private void loadFromFile(String fileName, String type) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                double price = Double.parseDouble(data[2]);
                int quantity = Integer.parseInt(data[3]);
                String manufacturer = data[4];

                if ("authentic".equals(type)) {
                    int warrantyPeriod = Integer.parseInt(data[5]);
                    String warrantyScope = data[6];
                    phones.add(new AuthenticPhone(id, name, price, quantity, manufacturer, warrantyPeriod, warrantyScope));
                } else if ("import".equals(type)) {
                    String importCountry = data[5];
                    String usageStatus = data[6];
                    phones.add(new ImportPhone(id, name, price, quantity, manufacturer, importCountry, usageStatus));
                }
                idCounter = Math.max(idCounter, id + 1);
            }
        } catch (IOException e) {
            System.err.println("Error loading phones: " + e.getMessage());
        }
    }

    private void saveToFile(String fileName, String type) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Phone phone : phones) {
                if (("authentic".equals(type) && phone instanceof AuthenticPhone) ||
                        ("import".equals(type) && phone instanceof ImportPhone)) {
                    bw.write(phone.toCSV());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving phones: " + e.getMessage());
        }
    }

    private Phone findPhoneById(int id) {
        for (Phone phone : phones) {
            if (phone.getId() == id) {
                return phone;
            }
        }
        return null;
    }

    public int getNextId() {
        return idCounter++;
    }
}
