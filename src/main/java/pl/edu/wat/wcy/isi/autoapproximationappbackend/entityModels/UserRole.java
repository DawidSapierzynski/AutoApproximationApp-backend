package pl.edu.wat.wcy.isi.autoapproximationappbackend.entityModels;

public enum UserRole {
    ADMIN("Admin"),
    USER("User");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
