package com.market.datastorage;

import com.market.newmarketapp.MarketApplication;

import java.io.*;

/**
 * Implements the DataStorage interface for session-level data storage.
 * Uses file operations to persist and retrieve serialized objects.
 */
public class SessionDataStorage implements DataStorage {

    // The file path for storing serialized objects.
    private final String productFileName;

    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private FileInputStream fis;
    private ObjectInputStream ois;

    /**
     * Constructor for SessionDataStorage.
     * Initializes the storage file based on the provided filename.
     *
     * @param fileName The name of the file used for session data storage.
     */
    public SessionDataStorage(String fileName) {
        this.productFileName = MarketApplication.class.getResource(fileName).getFile();
        System.out.println(productFileName);
    }

    /**
     * Writes a serializable object to the storage file.
     *
     * @param obj The object to be serialized and written to the file.
     * @throws RuntimeException if an I/O error occurs during the write operation.
     */
    @Override
    public void write(Serializable obj) {
        try {
            fos = new FileOutputStream(productFileName);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);

            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads and deserializes an object from the storage file.
     *
     * @return The deserialized object, or null if the file is empty.
     * @throws RuntimeException if an I/O or class-not-found error occurs during the read operation.
     */
    @Override
    public Serializable read() {
        Serializable obj;
        try {
            fis = new FileInputStream(productFileName);

            if (fis.available() == 0) {
                return null;
            }

            ois = new ObjectInputStream(fis);
            obj = (Serializable) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * Clears the content of the storage file.
     * Overwrites the file with an empty content.
     */
    @Override
    public void clear() {
        try (FileWriter fw = new FileWriter(productFileName, false)) {
            System.out.println(productFileName + " dati cancellati con successo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

