package main.java.by.epam.learn.entity;

public class Certificate {
    private String number;
    private String issueDate;
    private String expiryDate;
    private String registrar;

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getIssueDate() { return issueDate; }
    public void setIssueDate(String issueDate) { this.issueDate = issueDate; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getRegistrar() { return registrar; }
    public void setRegistrar(String registrar) { this.registrar = registrar; }
}
