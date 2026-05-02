package storage;

import model.Salad;
import exception.SaladException;

public class SaladConnector {
    private SaladStorage storage;

    public SaladConnector(String baseDirectory) {
        this.storage = new FileSaladStorage(baseDirectory);
    }

    // Сохранение салата
    public void saveSalad(Salad salad, String name) throws SaladException {
        if (salad == null) {
            throw new SaladException("Салат не может быть null");
        }

        String filename = name + ".ser";
        storage.saveSalad(salad, filename);
    }

    // Загрузка салата
    public Salad loadSalad(String name) throws SaladException {
        String filename = name + ".ser";

        if (!storage.exists(filename)) {
            throw new SaladException("Салат с именем '" + name + "' не найден");
        }

        return storage.loadSalad(filename);
    }

    // Удаление салата
    public boolean deleteSalad(String name) throws SaladException {
        String filename = name + ".ser";
        return storage.deleteSalad(filename);
    }

    // Список всех салатов
    public String[] getAllSaladNames() throws SaladException {
        return storage.listAllSalads();
    }

    // Проверка существования салата
    public boolean saladExists(String name) {
        String filename = name + ".ser";
        return storage.exists(filename);
    }
}
