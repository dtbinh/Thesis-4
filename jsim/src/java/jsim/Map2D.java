package jsim;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unchecked")
public class Map2D {
    private Random generator;
    private List<Coordinate> landmarks;
    private List<Coordinate> waypoints; //Waypoints should belong to robot! 
    private List<Coordinate> generateRandomPoints(double radius, int count) {
        java.util.ArrayList<Coordinate> points = new java.util.ArrayList<Coordinate>();
        for (int i = 0; i < count; i++) {
            points.add(Coordinate.RandomCoordinate(radius));
        }
        return points;
    }

    /**
     * Creates a new map
     * @param landmarkCount The number of landmarks on the map
     * @param waypointCount The number of waypoints for the robot to go to
     * @param radius Radius of the map - all landmarks are in this range
     */ 
    public Map2D(int landmarkCount, int waypointCount, double radius) {
        generator = new Random();
        this.landmarks = generateRandomPoints(radius, landmarkCount);
        this.waypoints = generateRandomPoints(radius, waypointCount);
    }

    public Map2D(List<Coordinate> landmarks, List<Coordinate> waypoints) {
        generator = null;
        this.landmarks = landmarks;
        this.waypoints = waypoints;
    }

    public java.util.Iterator<Coordinate> getWaypointIterator() {
        return waypoints.iterator();
    }

    public List<Coordinate> getVisibleLandmarks(Robot r) {
        List<Coordinate> visible = new java.util.ArrayList<Coordinate>();
        Coordinate position = r.getPosition();
        double angle = r.getHeading();
        double range = r.sensorRange();
        double arc = r.sensorArc();

        for (Coordinate lmk : this.landmarks) {
            if (lmk.isVisible(position, angle, range, arc)) {
                visible.add(lmk);
             }
        }
        return visible;
    }
}

