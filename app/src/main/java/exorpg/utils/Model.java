package exorpg.utils;

public abstract class Model {
    public int id = 0;

    public abstract boolean get();

    public abstract boolean get(int id);

    public abstract boolean save();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
