public class Doctor {
    private String id;
    private String name;
    private String specialization;
    private String contact;

    public Doctor(String id, String name, String specialization, String contact) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getContact() { return contact; }

    public void setName(String name) { this.name = name; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setContact(String contact) { this.contact = contact; }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Specialization: " + specialization + " | Contact: " + contact;
    }
}
