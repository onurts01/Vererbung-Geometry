/**
 * An n-dimensional point in an n-dimensional space.
 * 
 * REPRÄSENTIERT: Einen Punkt mit beliebig vielen Koordinaten
 * ERBT VON: Geometry (dimensions = Anzahl der Koordinaten)
 * VERWENDET IN: Volume (als Eckpunkte)
 * 
 * UNTERSCHIED zu Point2D:
 * - Point2D: Fest 2 Dimensionen (x, y)
 * - Point: Flexible n Dimensionen (Array)
 * 
 * BEISPIELE:
 * Point p3d = new Point(1, 2, 3);           // 3D
 * Point p5d = new Point(1, 2, 3, 4, 5);     // 5D
 * Point p10d = new Point(0,0,0,0,0,0,0,0,0,0); // 10D
 */
public class Point extends Geometry {
    
    /**
     * Array der Koordinaten.
     * 
     * LÄNGE = Anzahl der Dimensionen
     * INDEX: 0 bis (dimensions-1)
     * 
     * BEISPIEL für 3D-Punkt (1,2,3):
     * coordinates[0] = 1.0
     * coordinates[1] = 2.0
     * coordinates[2] = 3.0
     */
    private double[] coordinates;

    /**
     * Creates a new n-dimensional point with the given coordinates.
     * 
     * VARARGS-PARAMETER (double... coordinates):
     * - Kann beliebig viele double-Werte annehmen
     * - Wird intern als Array behandelt
     * 
     * AUFRUF-BEISPIELE:
     * new Point(1, 2)          → 2D
     * new Point(1, 2, 3)       → 3D
     * new Point(1, 2, 3, 4)    → 4D
     * 
     * SICHERHEIT:
     * - clone() erstellt Kopie → Eingabe-Array kann nicht von außen verändert werden
     * - Mindestens 2 Koordinaten erforderlich
     *
     * @param coordinates the coordinates of the point (at least 2)
     * @throws RuntimeException if less than 2 coordinates are provided
     */
    public Point(double... coordinates) {
        // Rufe Superklassen-Konstruktor mit Anzahl der Koordinaten
        super(coordinates.length);
        
        // ZUSÄTZLICHE VALIDIERUNG
        // (super() prüft bereits >= 2, aber zur Sicherheit nochmal)
        if (coordinates.length < 2) {
            throw new RuntimeException("At least 2 coordinates required");
        }
        
        // DEFENSIVE COPY: Kopiere Array
        // Warum? Sonst könnte Aufrufer das Array extern ändern!
        this.coordinates = coordinates.clone();
    }

    /**
     * Gibt eine Kopie aller Koordinaten zurück.
     * 
     * DEFENSIVE COPY: 
     * - Gibt clone() zurück, nicht das Original
     * - Verhindert, dass Aufrufer interne Daten ändern kann
     * 
     * BEISPIEL:
     * Point p = new Point(1, 2, 3);
     * double[] coords = p.getCoordinates();
     * coords[0] = 999;  // Ändert NICHT den Punkt!
     * 
     * @return Kopie des Koordinaten-Arrays
     */
    public double[] getCoordinates() {
        return coordinates.clone();
    }

    /**
     * Gibt eine einzelne Koordinate zurück.
     * 
     * DIREKTER ZUGRIFF auf ein Element (keine Kopie nötig für Primitive)
     * 
     * BEISPIEL:
     * Point p = new Point(1, 2, 3);
     * p.getCoordinate(0) → 1.0
     * p.getCoordinate(1) → 2.0
     * p.getCoordinate(2) → 3.0
     * 
     * @param index Index der Koordinate (0-basiert)
     * @return Koordinate an der Position index
     */
    public double getCoordinate(int index) {
        return coordinates[index];
    }

    /**
     * Gibt das Volumen des Punktes zurück.
     * 
     * Ein n-dimensionaler Punkt hat KEINE Ausdehnung.
     * 
     * @return 0.0 (Punkte haben kein Volumen)
     */
    @Override
    public double volume() {
        return 0.0;
    }

    /**
     * Umschließt diesen Punkt mit einer anderen Geometrie.
     * 
     * FUNKTIONSWEISE (n-dimensional):
     * 
     * FALL 1: other ist Point
     * → Erzeugt Volume, das beide Punkte enthält
     * → Analog zu Point2D → Rectangle
     * 
     * FALL 2: other ist Volume
     * → Erweitert Volume um diesen Punkt
     * 
     * ALGORITHMUS für Punkt + Punkt:
     * 1. Gehe durch alle Dimensionen i = 0 bis n-1
     * 2. Für jede Dimension: finde min und max Koordinate
     * 3. Erstelle Volume aus (min-Punkt, max-Punkt)
     * 
     * BEISPIEL in 3D:
     * p1 = (1, 2, 3)
     * p2 = (4, 1, 5)
     * →
     * minCoords = (min(1,4), min(2,1), min(3,5)) = (1, 1, 3)
     * maxCoords = (max(1,4), max(2,1), max(3,5)) = (4, 2, 5)
     * → Volume von (1,1,3) bis (4,2,5)
     *
     * @param other die zu umschließende Geometrie
     * @return Volume oder null
     */
    @Override
    public Geometry encapsulate(Geometry other) {
        // SCHRITT 1: Validierung
        if (other == null || this.dimensions() != other.dimensions()) {
            return null;
        }

        if (other instanceof Point) {
            // FALL 1: Zwei n-dimensionale Punkte umschließen
            Point otherPoint = (Point) other;
            
            // Erstelle Arrays für min/max Koordinaten
            double[] minCoords = new double[this.dimensions()];
            double[] maxCoords = new double[this.dimensions()];
            
            // SCHLEIFE über alle Dimensionen
            for (int i = 0; i < this.dimensions(); i++) {
                // Für Dimension i: finde Minimum und Maximum
                minCoords[i] = Math.min(this.coordinates[i], otherPoint.coordinates[i]);
                maxCoords[i] = Math.max(this.coordinates[i], otherPoint.coordinates[i]);
            }
            
            // Erstelle Volume aus Ecken
            return new Volume(new Point(minCoords), new Point(maxCoords));
            
        } else if (other instanceof Volume) {
            // FALL 2: Volume erweitern um diesen Punkt
            // DELEGATION an Volume
            return other.encapsulate(this);
            
        } else {
            // Unbekannter Typ
            throw new RuntimeException("Unknown Geometry type");
        }
    }

    /**
     * Textuelle Repräsentation des Punktes.
     * 
     * FORMAT: Point(coord0, coord1, ..., coordN)
     * 
     * BEISPIELE:
     * Point(1.00, 2.00)              // 2D
     * Point(1.00, 2.00, 3.00)        // 3D
     * Point(1.00, 2.00, 3.00, 4.00)  // 4D
     * 
     * @return String-Repräsentation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Point(");
        
        // Gehe durch alle Koordinaten
        for (int i = 0; i < coordinates.length; i++) {
            sb.append(String.format("%.2f", coordinates[i]));
            
            // Füge Komma hinzu, außer bei letzter Koordinate
            if (i < coordinates.length - 1) {
                sb.append(", ");
            }
        }
        
        sb.append(")");
        return sb.toString();
    }
}
