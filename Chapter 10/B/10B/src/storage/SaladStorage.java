package storage;

import model.Salad;
import exception.SaladException;

public interface SaladStorage {
    void saveSalad(Salad salad, String filename) throws SaladException;
    Salad loadSalad(String filename) throws SaladException;
    boolean deleteSalad(String filename) throws SaladException;
    boolean exists(String filename);
    String[] listAllSalads() throws SaladException;
}