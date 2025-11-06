/**
 * An n-dimensional volume defined by two corner points.
 * The edges are axis-parallel.
 * 
 * REPRÄSENTIERT: Ein achsenparalleles n-dimensionales Volumen (Hyperquader)
 * ERBT VON: Geometry (dimensions = Anzahl der Dimensionen der Punkte)
 * DEFINIERT DURCH: Zwei diagonal gegenüberliegende Eckpunkte
 * 
 * BEZIEHUNG zu Rectangle:
 * - Rectangle ist SPEZIELLER Fall: Volume in 2D
 * - Volume ist ALLGEMEIN: Funktioniert in 2D, 3D, 4D, ..., nD
 * 
 * EIGENSCHAFTEN:
 * - Alle Kanten parallel zu Koordinatenachsen
 * - Volumen = Produkt aller Kantenlängen
 * 
 * BEISPIELE:
 * 2D: Volume von (0,0) bis (4,3) → Rectangle mit Fläche 12
 * 3D: Volume von (0,0,0) bis (2,3,4) → Quader mit Volumen 24
 * 4D: Volume von (0,0,0,0) bis (2,2,2,2) → Hyperwürfel mit Volumen 16
 */
public class Volume extends Geometry {
    
    /**
     * Untere Ecke des Volumens.
     * INVARIANTE: Für alle Dimensionen i gilt:
     *   lowerCorner.getCoordinate(i) <= upperCorner.getCoordinate(i)
     */
    private Point lowerCorner;
    
    /**
     * Obere Ecke des Volumens.
     * INVARIANTE: Für alle Dimensionen i gilt:
     *   upperCorner.getCoordinate(i) >= lowerCorner.getCoordinate(i)
     */
    private Point upperCorner;

    /**
     * Creates a new n-dimensional volume from two corner points.
     * 
     * KONSTRUKTOR-LOGIK (analog zu Rectangle):
     * 1. Prüfe: Beide Punkte müssen gleiche Dimensionen haben
     * 2. Normalisiere: Bestimme wahre min/max für JEDE Dimension
     * 3. Erstelle normalisierte Eckpunkte
     * 
     * WARUM NORMALISIERUNG?
     * - Benutzer könnte Punkte in beliebiger Reihenfolge übergeben
     * - Wir garantieren IMMER: lowerCorner hat minimale Koordinaten
     * 
     * BEISPIEL in 3D:
     * p1 = (5, 1, 3)
     * p2 = (1, 4, 2)
     * →
     * lowerCorner = (1, 1, 2)  // Minimum jeder Dimension
     * upperCorner = (5, 4, 3)  // Maximum jeder Dimension
     *
     * @param p1 first corner point
     * @param p2 second corner point (opposite corner)
     * @throws RuntimeException if points have different dimensions
     */
    public Volume(Point p1, Point p2) {
        // Rufe Superklassen-Konstruktor mit Dimension des ersten Punktes
        super(p1.dimensions());
        
        // VALIDIERUNG: Punkte müssen gleiche Dimension haben
        if (p1.dimensions() != p2.dimensions()) {
            throw new RuntimeException("Points must have same dimensions");
        }
        
        // Anzahl der Dimensionen
        int dim = p1.dimensions();
        
        // Arrays für normalisierte Koordinaten
        double[] minCoords = new double[dim];
        double[] maxCoords = new double[dim];
        
        // NORMALISIERUNG: Für jede Dimension min/max bestimmen
        for (int i = 0; i < dim; i++) {
            minCoords[i] = Math.min(p1.getCoordinate(i), p2.getCoordinate(i));
            maxCoords[i] = Math.max(p1.getCoordinate(i), p2.getCoordinate(i));
        }
        
        // Erstelle normalisierte Eckpunkte
        this.lowerCorner = new Point(minCoords);
        this.upperCorner = new Point(maxCoords);
    }

    /**
     * Gibt die untere Ecke zurück.
     * 
     * @return untere Ecke
     */
    public Point getLowerCorner() {
        return lowerCorner;
    }

    /**
     * Gibt die obere Ecke zurück.
     * 
     * @return obere Ecke
     */
    public Point getUpperCorner() {
        return upperCorner;
    }

    /**
     * Gibt die Kantenlänge in einer bestimmten Dimension zurück.
     * 
     * BERECHNUNG: Differenz der Koordinaten in dieser Dimension
     * 
     * BEISPIEL für 3D-Quader von (0,0,0) bis (2,3,4):
     * getEdgeLength(0) → 2  (Länge in X-Richtung)
     * getEdgeLength(1) → 3  (Länge in Y-Richtung)
     * getEdgeLength(2) → 4  (Länge in Z-Richtung)
     * 
     * @param dimension Index der Dimension (0-basiert)
     * @return Kantenlänge in dieser Dimension
     */
    public double getEdgeLength(int dimension) {
        return upperCorner.getCoordinate(dimension) - lowerCorner.getCoordinate(dimension);
    }

    /**
     * Gibt das Volumen des n-dimensionalen Körpers zurück.
     * 
     * BERECHNUNG: Produkt aller Kantenlängen
     * FORMEL: volume = Länge₀ × Länge₁ × ... × Längeₙ₋₁
     * 
     * BEISPIELE:
     * 2D: (0,0)-(4,3) → 4 × 3 = 12 (Fläche)
     * 3D: (0,0,0)-(2,3,4) → 2 × 3 × 4 = 24 (Volumen)
     * 4D: (0,0,0,0)-(2,2,2,2) → 2 × 2 × 2 × 2 = 16 (Hypervolumen)
     * 
     * @return Volumen (Produkt aller Kantenlängen)
     */
    @Override
    public double volume() {
        // Starte mit 1 (neutrales Element der Multiplikation)
        double vol = 1.0;
        
        // Multipliziere alle Kantenlängen
        for (int i = 0; i < dimensions(); i++) {
            vol *= getEdgeLength(i);
        }
        
        return vol;
    }

    /**
     * Umschließt dieses Volume mit einer anderen Geometrie.
     * 
     * FUNKTIONSWEISE (analog zu Rectangle):
     * 
     * FALL 1: other ist Point
     * → Erweitert Volume um den Punkt
     * 
     * FALL 2: other ist Volume
     * → Erzeugt größeres Volume, das beide enthält
     * 
     * ALGORITHMUS:
     * 1. Für jede Dimension i:
     *    - Finde Minimum aller beteiligten Koordinaten
     *    - Finde Maximum aller beteiligten Koordinaten
     * 2. Erstelle neues Volume aus (min-Punkt, max-Punkt)
     * 
     * BEISPIEL in 3D:
     * Volume1: (0,0,0)-(2,2,2)
     * Volume2: (1,1,1)-(3,3,3)
     * →
     * Neues Volume: (0,0,0)-(3,3,3)
     *
     * @param other die zu umschließende Geometrie
     * @return erweitertes Volume oder null
     */
    @Override
    public Geometry encapsulate(Geometry other) {
        // SCHRITT 1: Validierung
        if (other == null || this.dimensions() != other.dimensions()) {
            return null;
        }

        if (other instanceof Point) {
            // FALL 1: Volume + Point → erweitertes Volume
            Point otherPoint = (Point) other;
            
            // Arrays für neue Ecken
            double[] minCoords = new double[dimensions()];
            double[] maxCoords = new double[dimensions()];
            
            // Für jede Dimension: finde neues Min/Max
            for (int i = 0; i < dimensions(); i++) {
                // Berücksichtige: dieses Volume UND den neuen Punkt
                minCoords[i] = Math.min(lowerCorner.getCoordinate(i), otherPoint.getCoordinate(i));
                maxCoords[i] = Math.max(upperCorner.getCoordinate(i), otherPoint.getCoordinate(i));
            }
            
            // Erstelle neues, möglicherweise größeres Volume
            return new Volume(new Point(minCoords), new Point(maxCoords));
            
        } else if (other instanceof Volume) {
            // FALL 2: Volume + Volume → größeres Volume
            Volume otherVol = (Volume) other;
            
            // Arrays für neue Ecken
            double[] minCoords = new double[dimensions()];
            double[] maxCoords = new double[dimensions()];
            
            // Für jede Dimension: finde Min/Max über BEIDE Volumes
            for (int i = 0; i < dimensions(); i++) {
                minCoords[i] = Math.min(
                    lowerCorner.getCoordinate(i),
                    otherVol.lowerCorner.getCoordinate(i)
                );
                maxCoords[i] = Math.max(
                    upperCorner.getCoordinate(i),
                    otherVol.upperCorner.getCoordinate(i)
                );
            }
            
            // Erstelle umschließendes Volume
            return new Volume(new Point(minCoords), new Point(maxCoords));
            
        } else {
            // Unbekannter Typ
            throw new RuntimeException("Unknown Geometry type");
        }
    }

    /**
     * Textuelle Repräsentation des Volumes.
     * 
     * FORMAT: Volume[lowerCorner, upperCorner] (Volume: volumen)
     * 
     * BEISPIELE:
     * 2D: Volume[Point(0.00, 0.00), Point(4.00, 3.00)] (Volume: 12.00)
     * 3D: Volume[Point(0.00, 0.00, 0.00), Point(2.00, 3.00, 4.00)] (Volume: 24.00)
     * 
     * @return String-Repräsentation
     */
    @Override
    public String toString() {
        return String.format("Volume[%s, %s] (Volume: %.2f)", 
            lowerCorner, upperCorner, volume());
    }
}
