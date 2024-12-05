package com.market.datastorage;

import java.io.Serializable;

/**
 * Interface for file operations.
 */
public interface DataStorage {

    /**
     * Writes a serializable object to a file.
     *
     * @param obj the object to write.
     */
    void write(Serializable obj);

    /**
     * Reads a serializable object from a file.
     *
     * @return the object read, or null if none.
     */
    Serializable read();

    /**
     * Clears the file content.
     */
    void clear();
}
