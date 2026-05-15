package main.java.by.epam.learn.entity;

import java.util.ArrayList;
import java.util.List;

public class Medicine {
    private String id;
    private String name;
    private String pharm;
    private String group;
    private List<String> analogs = new ArrayList<>();
    private List<Version> versions = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPharm() { return pharm; }
    public void setPharm(String pharm) { this.pharm = pharm; }

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    public List<String> getAnalogs() { return analogs; }
    public void setAnalogs(List<String> analogs) { this.analogs = analogs; }
    public void addAnalog(String analog) { this.analogs.add(analog); }

    public List<Version> getVersions() { return versions; }
    public void setVersions(List<Version> versions) { this.versions = versions; }
    public void addVersion(Version version) { this.versions.add(version); }

    public double getMinPrice() {
        if (versions.isEmpty()) return 0;
        double min = Double.MAX_VALUE;
        for (Version v : versions) {
            if (v.getPkg() != null && v.getPkg().getPrice() < min) {
                min = v.getPkg().getPrice();
            }
        }
        return min;
    }
}