package storage;

import model.Salad;
import exception.SaladException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSaladStorage implements SaladStorage {
    private String baseDirectory;

    public FileSaladStorage(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        File dir = new File(baseDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void saveSalad(Salad salad, String filename) throws SaladException {
        String fullPath = baseDirectory + File.separator + filename;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
            oos.writeObject(salad);
        } catch (IOException e) {
            throw new SaladException("Ошибка при сохранении салата: " + e.getMessage(), e);
        }
    }

    @Override
    public Salad loadSalad(String filename) throws SaladException {
        String fullPath = baseDirectory + File.separator + filename;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
            return (Salad) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new SaladException("Файл не найден: " + filename, e);
        } catch (IOException | ClassNotFoundException e) {
            throw new SaladException("Ошибка при загрузке салата: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteSalad(String filename) throws SaladException {
        String fullPath = baseDirectory + File.separator + filename;
        File file = new File(fullPath);

        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    @Override
    public boolean exists(String filename) {
        String fullPath = baseDirectory + File.separator + filename;
        return new File(fullPath).exists();
    }

    @Override
    public String[] listAllSalads() throws SaladException {
        File dir = new File(baseDirectory);
        String[] files = dir.list((d, name) -> name.endsWith(".ser"));

        if (files == null) {
            return new String[0];
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].endsWith(".ser")) {
                files[i] = files[i].substring(0, files[i].lastIndexOf('.'));
            }
        }
        return files;
    }
}
