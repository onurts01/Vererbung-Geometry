/**
 * A two-dimensional point in a 2D space.
 * 
 * REPRÄSENTIERT: Einen Punkt mit X- und Y-Koordinate
 * ERBT VON: Geometry (dimensions = 2)
 * VERWENDET IN: Rectangle (als Eckpunkte)
 * 
 * BEISPIEL: Point2D(3.0, 5.0) ist ein Punkt bei (3,5) im 2D-Raum
 */
public class Point2D extends Geometry {
    
    /**
     * X-Koordinate des Punktes.
     * PRIVATE = Information Hiding, nur über getX() zugreifbar
     */
    private double x;
    
    /**
     * Y-Koordinate des Punktes.
     * PRIVATE = Information Hiding, nur über getY() zugreifbar
     */
    private double y;

    /**
     * Creates a new 2D point with the given coordinates.
     * 
     * KONSTRUKTOR-ABLAUF:
     * 1. Ruft super(2) auf → Geometry-Konstruktor mit dimension=2
     * 2. Speichert x und y Koordinaten
     * 
     * BEISPIEL:
     * Point2D p = new Point2D(3.0, 5.0);
     * → Punkt bei (3, 5)
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Point2D(double x, double y) {
        // Rufe Superklassen-Konstruktor mit 2 Dimensionen auf
        super(2);
        this.x = x;
        this.y = y;
    }

    /**
     * Gibt die X-Koordinate zurück.
     * 
     * GETTER für private Variable x
     * 
     * @return X-Koordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gibt die Y-Koordinate zurück.
     * 
     * GETTER für private Variable y
     * 
     * @return Y-Koordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Gibt das Volumen des Punktes zurück.
     * 
     * WICHTIG: Ein Punkt hat KEINE Ausdehnung!
     * - Mathematisch: Ein Punkt ist nulldimensional in Bezug auf Volumen
     * - Deshalb: volume() gibt immer 0.0 zurück
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
     * FUNKTIONSWEISE (siehe Aufgabenbilder):
     * 
     * FALL 1: other ist Point2D
     * → Erzeugt Rectangle, das beide Punkte enthält
     * → Entspricht Bild (a) "Umfassung von zwei Punkten"
     * 
     * FALL 2: other ist Rectangle
     * → Erweitert Rectangle um diesen Punkt
     * → Entspricht Bild (b) "Umfassung von einem Punkt und einem Rechteck"
     * 
     * FALL 3: Unterschiedliche Dimensionen
     * → return null
     * 
     * ALGORITHMUS für Punkt + Punkt:
     * 1. Finde minimale X/Y Koordinaten (untere linke Ecke)
     * 2. Finde maximale X/Y Koordinaten (obere rechte Ecke)
     * 3. Erstelle Rectangle aus diesen Ecken
     *
     * @param other die zu umschließende Geometrie
     * @return Rectangle oder null
     */
    @Override
    public Geometry encapsulate(Geometry other) {
        // SCHRITT 1: Validierung
        // Prüfe ob other null ist oder unterschiedliche Dimensionen hat
        if (other == null || this.dimensions() != other.dimensions()) {
            return null;  // Ungültig → null zurückgeben
        }

        // SCHRITT 2: Typprüfung und Umschließung
        if (other instanceof Point2D) {
            // FALL 1: Zwei Punkte umschließen
            Point2D otherPoint = (Point2D) other;  // Typecast
            
            // Berechne Bounding Box (kleinster umschließender Bereich)
            double minX = Math.min(this.x, otherPoint.x);  // Linke Seite
            double maxX = Math.max(this.x, otherPoint.x);  // Rechte Seite
            double minY = Math.min(this.y, otherPoint.y);  // Untere Seite
            double maxY = Math.max(this.y, otherPoint.y);  // Obere Seite
            
            // Erstelle Rectangle aus Ecken
            return new Rectangle(new Point2D(minX, minY), new Point2D(maxX, maxY));
            
        } else if (other instanceof Rectangle) {
            // FALL 2: Rectangle erweitern um
