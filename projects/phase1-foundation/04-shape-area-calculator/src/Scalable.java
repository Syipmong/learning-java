/**
 * Interface for shapes that can be scaled
 */
public interface Scalable {
    void scale(double factor);
    Shape createScaledCopy(double factor);
    double getScaleFactor();
}
