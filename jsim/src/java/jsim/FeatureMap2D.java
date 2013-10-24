package jsim;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unchecked")
public class FeatureMap2D {
    private Random generator;
    private List<Landmark> landmarks;
    private List<Coordinate> waypoints; //Waypoints should belong to robot!

    private List<Coordinate> generateRandomPoints(double radius, int count) {
        java.util.ArrayList<Coordinate> points = new java.util.ArrayList<Coordinate>();
        for (int i = 0; i < count; i++) {
            points.add(Coordinate.RandomCoordinate(radius));
        }
        return points;
    }

    private List<Landmark> generateRandomLandmarks(double radius, int count) {
        java.util.ArrayList<Landmark> points = new java.util.ArrayList<Landmark>();
        for (int i = 0; i < count; i++) {
            Coordinate c = Coordinate.RandomCoordinate(radius);
            points.add(new Landmark(c.getX(), c.getY(), i));
        }
        return points;
    }

    /**
     * Creates a new map
     * @param landmarkCount The number of landmarks on the map
     * @param waypointCount The number of waypoints for the robot to go to
     * @param radius Radius of the map - all landmarks are in this range
     */ 
    public FeatureMap2D(int landmarkCount, int waypointCount, double radius) {
        generator = new Random();
        this.landmarks = generateRandomLandmarks(radius, landmarkCount);
        this.waypoints = generateRandomPoints(radius, waypointCount);
    }

    public FeatureMap2D(List<Landmark> landmarks, List<Coordinate> waypoints) {
        generator = null;
        this.landmarks = landmarks;
        this.waypoints = waypoints;
    }

    public int landmarkCount() {
        return landmarks.size();
    }

    public List<Landmark> getLandmarks() {
        return new java.util.ArrayList(landmarks);
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

