public class Supply {
    private String id;
    private String name;
    private String quantity;

    public Supply(String id, String name, String quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + quantity;
    }
}
