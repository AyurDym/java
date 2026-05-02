package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String bio;
    private List<Poem> poems;

    public Author(String name) {
        this.name = name;
        this.poems = new ArrayList<>();
        this.bio = "";
    }

    public Author(String name, String bio) {
        this.name = name;
        this.bio = bio;
        this.poems = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public List<Poem> getPoems() { return poems; }
    public void setPoems(List<Poem> poems) { this.poems = poems; }

    public void addPoem(Poem poem) {
        if (!poem.getAuthor().equals(name)) {
            poem.setAuthor(name);
        }
        poems.add(poem);
    }

    public void removePoem(String title) {
        poems.removeIf(poem -> poem.getTitle().equals(title));
    }

    public Poem findPoem(String title) {
        return poems.stream()
                .filter(poem -> poem.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public int getTotalLinesCount() {
        return poems.stream()
                .mapToInt(poem -> poem.getLines().size())
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== АВТОР: ").append(name.toUpperCase()).append(" ===\n");
        if (!bio.isEmpty()) {
            sb.append("Биография: ").append(bio).append("\n");
        }
        sb.append("Количество стихотворений: ").append(poems.size()).append("\n");
        sb.append("Всего строк: ").append(getTotalLinesCount()).append("\n");
        sb.append("Стихотворения:\n");

        for (Poem poem : poems) {
            sb.append("  - \"").append(poem.getTitle()).append("\" (")
                    .append(poem.getLines().size()).append(" строк)\n");
        }

        return sb.toString();
    }
}