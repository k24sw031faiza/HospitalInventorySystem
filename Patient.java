public class Patient {
    private String id;
    private String name;
    private String disease;
    private String gender;
    private String age;

    public Patient(String id, String name, String disease, String gender, String age) {
        this.id = id;
        this.name = name;
        this.disease = disease;
        this.gender = gender;
        this.age = age;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDisease() { return disease; }
    public String getGender() { return gender; }
    public String getAge() { return age; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAge(String age) { this.age = age; }

    // toString override for display (optional)
    @Override
    public String toString() {
        return id + ", " + name + ", " + disease + ", " + gender + ", " + age;
    }
}
