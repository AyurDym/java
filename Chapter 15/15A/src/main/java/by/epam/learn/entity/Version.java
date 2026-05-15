package main.java.by.epam.learn.entity;

public class Version {
    private String type;
    private Certificate certificate;
    private MedPackage pkg;
    private Dosage dosage;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Certificate getCertificate() { return certificate; }
    public void setCertificate(Certificate certificate) { this.certificate = certificate; }

    public MedPackage getPkg() { return pkg; }
    public void setPkg(MedPackage pkg) { this.pkg = pkg; }

    public Dosage getDosage() { return dosage; }
    public void setDosage(Dosage dosage) { this.dosage = dosage; }
}
