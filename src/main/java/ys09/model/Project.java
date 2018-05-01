package ys09.model;

public class Project {

    private final long id;
    private final long ownerId;
    private final String name;
    private final String description;

    public Project(long id, long ownerId, String name, String description) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
