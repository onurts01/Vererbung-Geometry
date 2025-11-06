/**
 * A rectangle in 2D space defined by two corner points.
 * 
 * REPRÄSENTIERT: Ein achsenparalleles Rechteck im 2D-Raum
 * ERBT VON: Geometry (dimensions = 2)
 * DEFINIERT DURCH: Zwei diagonal gegenüberliegende Eckpunkte
 * 
 * WICHTIGE EIGENSCHAFTEN:
 * - Kanten sind parallel zu den Achsen (nicht rotiert)
 * - Wird durch untere linke und obere rechte Ecke gespeichert
 * - Volumen = Fläche = Breite × Höhe
 * 
 * BEISPIEL:
 * Rectangle rect = new Rectangle(
 *     new Point2D(0, 0),
 *     new Point2D(4, 3)
 * );
 * → Rechteck von (0,0) bis (4,3) mit Fläche 12
 */
public class Rectangle extends Geometry {
    
    /**
     * Untere linke Ecke des Rechtecks.
     * INVARIANTE: lowerLeft.x <= upperRight.x und lowerLeft.y <= upperRight.y
     */
    private Point2D lowerLeft;
    
    /**
     * Obere rechte Ecke des Rechtecks.
     * INVARIANTE: upperRight.x >= lowerLeft.x und upperRight.y >= lowerLeft.y
     */
    private Point2D upperRight;

    /**
     * Creates a new rectangle from two corner points.
     * 
     * KONSTRUKTOR-LOGIK:
     * 1. Ruft super(2) auf (2D-Geometrie)
     * 2. Normalisiert die Eingabe: Egal welche Punkte übergeben werden,
     *    es wird immer korrekt lowerLeft und upperRight gesetzt
     * 3. Erstellt neue Point2D-Objekte (Defensive Copy)
     * 
     * WARUM NORMALISIERUNG?
     * - Benutzer könnte Punkte in beliebiger Reihenfolge übergeben
     * - Wir wollen IMMER garantieren: lowerLeft ist unten links
     * 
     * BEISPIEL:
     * Rectangle(Point2D(5,5), Point2D(1,1))  ← "falsche" Reihenfolge
     * → wird intern zu lowerLeft(1,1), upperRight(5,5)
     *
     * @param p1 first corner point
     * @param p2 second corner point (opposite corner)
     */
    public Rectangle(Point2D p1, Point2D p2) {
        // Rufe Superklassen-Konstruktor
        super(2);
        
        // NORMALISIERUNG: Finde wahre min/max Werte
        // (egal in welcher Reihenfolge p1 und p2 übergeben wurden)
        double minX = Math.min(p1.getX(), p2.getX());  // Kleinster X-Wert
        double maxX = Math.max(p1.getX(), p2.getX());  // Größter X-Wert
        double minY = Math.min(p1.getY(), p2.getY());  // Kleinster Y-Wert
        double maxY = Math.max(p1.getY(), p2.getY());  // Größter Y-Wert
        
        // Erstelle normalisierte Eckpunkte
        // lowerLeft = Punkt mit minimalen Koordinaten (unten links)
        this.lowerLeft = new Point2D(minX, minY);
        // upperRight = Punkt mit maximalen Koordinaten (oben rechts)
        this.upperRight = new Point2D(maxX, maxY);
    }

    /**
     * Gibt die untere linke Ecke zurück.
     * 
     * @return untere linke Ecke
     */
    public Point2D getLowerLeft() {
        return lowerLeft;
    }

    /**
     * Gibt die obere rechte Ecke zurück.
     * 
     * @return obere rechte Ecke
     */
    public Point2D getUpperRight() {
        return upperRight;
    }

    /**
     * Gibt die Breite des Rechtecks zurück.
     * 
     * BERECHNUNG: Differenz der X-Koordinaten
     * 
     * @return Breite (in X-Richtung)
     */
    public double getWidth() {
        return upperRight.getX() - lowerLeft.getX();
    }

    /**
     * Gibt die Höhe des Rechtecks zurück.
     * 
     * BERECHNUNG: Differenz der Y-Koordinaten
     * 
     * @return Höhe (in Y-Richtung)
     */
    public double getHeight() {
        return upperRight.getY() - lowerLeft.getY();
    }

    /**
     * Gibt das Volumen (= Fläche) des Rechtecks zurück.
     * 
     * BERECHNUNG: Breite × Höhe
     * 
     * BEISPIEL:
     * Rectangle von (0,0) bis (4,3)
     * → Breite = 4, Höhe = 3
     * → volume() = 12.0
     *
     * @return Fläche des Rechtecks
     */
    @Override
    public double volume() {
        // Für 2D ist "Volumen" = Fläche
        return getWidth() * getHeight();
    }

    /**
     * Umschließt dieses Rechteck mit einer anderen Geometrie.
     * 
     * FUNKTIONSWEISE (siehe Aufgabenbilder):
     * 
     * FALL 1: other ist Point2D
     * → Erweitert Rectangle um den Punkt
     * → Entspricht Bild (b)
     * 
     * FALL 2: other ist Rectangle
     * → Erzeugt größeres Rectangle, das beide enthält
     * → Entspricht Bild (c) "Umfassung von zwei Rechtecken"
     * 
     * ALGORITHMUS:
     * 1. Finde minimale X/Y über alle Punkte
     * 2. Finde maximale X/Y über alle Punkte
     * 3. Neues Rectangle aus (min, max)
     *
     * @param other die zu umschließende Geometrie
     * @return erweitertes Rectangle oder null
     */
    @Override
    public Geometry encapsulate(Geometry other) {
        // SCHRITT 1: Validierung
        if (other == null || this.dimensions() != other.dimensions()) {
            return null;
        }

        if (other instanceof Point2D) {
            // FALL 1: Rectangle + Point → erweitertes Rectangle
            Point2D otherPoint = (Point2D) other;
            
            // Berechne neue Bounding Box
            // Berücksichtige: dieses Rectangle UND den neuen Punkt
            double minX = Math.min(this.lowerLeft.getX(), otherPoint.getX());
            double maxX = Math.max(this.upperRight.getX(), otherPoint.getX());
            double minY = Math.min(this.lowerLeft.getY(), otherPoint.getY());
            double maxY = Math.max(this.upperRight.getY(), otherPoint.getY());
            
            // Erstelle neues, möglicherweise größeres Rectangle
            return new Rectangle(new Point2D(minX, minY), new Point2D(maxX, maxY));
            
        } else if (other instanceof Rectangle) {
            // FALL 2: Rectangle + Rectangle → größeres Rectangle
            Rectangle otherRect = (Rectangle) other;
            
            // Finde Minimum/Maximum über BEIDE Rectangles
            double minX = Math.min(this.lowerLeft.getX(), otherRect.lowerLeft.getX());
            double maxX = Math.max(this.upperRight.getX(), otherRect.upperRight.getX());
            double minY = Math.min(this.lowerLeft.getY(), otherRect.lowerLeft.getY());
            double maxY = Math.max(this.upperRight.getY(), otherRect.upperRight.getY());
            
            // Erstelle umschließendes Rectangle
            return new Rectangle(new Point2D(minX, minY), new Point2D(maxX, maxY));
            
        } else {
            // Unbekannter Typ
            throw new RuntimeException("Unknown Geometry type");
        }
    }

    /**
     * Textuelle Repräsentation des Rechtecks.
     * 
     * FORMAT: Rectangle[lowerLeft, upperRight] (Area: fläche)
     * BEISPIEL: Rectangle[Point2D(0.00, 0.00), Point2D(4.00, 3.00)] (Area: 12.00)
     * 
     * @return String-Repräsentation
     */
    @Override
    public String toString() {
        return String.format("Rectangle[%s, %s] (Area: %.2f)", 
            lowerLeft, upperRight, volume());
    }
}
