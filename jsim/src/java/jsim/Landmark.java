package jsim;

public class Landmark {
    private Coordinate location;
    private int id;

    public Landmark(Coordinate c, int id) {
        location = c;
        this.id = id;
    }

    public double getX() {
        return location.getX();
    }
    public double getY() {
        return location.getY();
    }
    public int getId() {
        return id;
    }
}
