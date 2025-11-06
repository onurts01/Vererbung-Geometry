/**
 * Test class for Geometry hierarchy.
 * 
 * ZWECK:
 * - Automatisiertes Testen aller implementierten Funktionen
 * - Demonstration der Verwendung
 * - Validierung der Korrektheit
 * 
 * STRUKTUR:
 * - Jede Testmethode testet einen Aspekt der Bibliothek
 * - Verwendet Assertions für automatische Fehlerprüfung
 * - Gibt Ergebnisse zur manuellen Inspektion aus
 * 
 * VERWENDUNG:
 * java -ea GeometryTest    (-ea aktiviert Assertions)
 */
public class GeometryTest {

    /**
     * Hauptmethode - führt alle Tests aus.
     * 
     * ABLAUF:
     * 1. Teste jede Klasse einzeln
     * 2. Teste Comparable-Funktionalität
     * 3. Teste Encapsulation in allen Varianten
     * 4. Erfolgreiche Ausgabe wenn alle Tests bestanden
     */
    public static void main(String[] args) {
        System.out.println("=== Geometry Library Test Suite ===\n");
        
        // Teste einzelne Klassen
        testPoint2D();
        testRectangle();
        testNDimensionalPoint();
        testVolume();
        
        // Teste Interfaces und Interaktionen
        testComparable();
        testEncapsulation();
        
        // Erfolgsmeldung
        System.out.println("\n✓ All tests passed!");
        System.out.println("=== Test Suite Complete ===");
    }

    /**
     * Testet Point2D-Funktionalität.
     * 
     * GETESTET:
     * - Konstruktor
     * - dimensions() gibt 2 zurück
     * - volume() gibt 0.0 zurück
     * - Getter-Methoden
     */
    private static void testPoint2D() {
        System.out.println("Testing Point2D...");
        
        // Erstelle Testpunkt
        Point2D p1 = new Point2D(1.0, 2.0);
        
        // Teste Dimensionen
        assert p1.dimensions() == 2 : "Point2D should have 2 dimensions";
        
        // Teste Volumen (sollte 0 sein)
        assert p1.volume() == 0.0 : "Point2D should have volume 0";
        
        // Teste Getter
        assert p1.getX() == 1.0 : "X coordinate should be 1.0";
        assert p1.getY() == 2.0 : "Y coordinate should be 2.0";
        
        // Ausgabe zur manuellen Inspektion
        System.out.println("  Point2D: " + p1);
        System.out.println("  ✓ Point2D tests passed\n");
    }

    /**
     * Testet Rectangle-Funktionalität.
     * 
     * GETESTET:
     * - Konstruktor mit zwei Punkten
     * - dimensions() gibt 2 zurück
     * - volume() berechnet Fläche korrekt
     * - Getter-Methoden
     */
    private static void testRectangle() {
        System.out.println("Testing Rectangle...");
        
        // Erstelle Testrechteck von (0,0) bis (4,3)
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(4, 3);
        Rectangle rect = new Rectangle(p1, p2);
        
        // Teste Dimensionen
        assert rect.dimensions() == 2 : "Rectangle should have 2 dimensions";
        
        // Teste Volumen: 4 × 3 = 12
        assert rect.volume() == 12.0 : "Expected 12.0 but got " + rect.volume();
        
        // Teste Breite und Höhe
        assert rect.getWidth() == 4.0 : "Width should be 4.0";
        assert rect.getHeight() == 3.0 : "Height should be 3.0";
        
        // Ausgabe
        System.out.println("  Rectangle: " + rect);
        System.out.println("  ✓ Rectangle tests passed\n");
    }

    /**
     * Testet N-Dimensional Point-Funktionalität.
     * 
     * GETESTET:
     * - Konstruktor mit variabler Anzahl Koordinaten
     * - dimensions() gibt korrekte Anzahl zurück
     * - volume() gibt 0.0 zurück
     * - Getter für einzelne Koordinaten
     */
    private static void testNDimensionalPoint() {
        System.out.println("Testing N-Dimensional Point...");
        
        // Erstelle 3D-Punkt
        Point p = new Point(1.0, 2.0, 3.0);
        
        // Teste Dimensionen
        assert p.dimensions() == 3 : "Point should have 3 dimensions";
        
        // Teste Volumen
        assert p.volume() == 0.0 : "Point should have volume 0";
        
        // Teste Koordinaten-Zugriff
        assert p.getCoordinate(0) == 1.0 : "Coordinate 0 should be 1.0";
        assert p.getCoordinate(1) == 2.0 : "Coordinate 1 should be 2.0";
        assert p.getCoordinate(2) == 3.0 : "Coordinate 2 should be 3.0";
        
        // Ausgabe
        System.out.println("  3D Point: " + p);
        System.out.println("  ✓ N-Dimensional Point tests passed\n");
    }

    /**
     * Testet Volume-Funktionalität.
     * 
     * GETESTET:
     * - Konstruktor mit zwei n-dimensionalen Punkten
     * - dimensions() gibt korrekte Anzahl zurück
     * - volume() berechnet Produkt aller Kantenlängen korrekt
     * - Kantenlängen-Getter
     */
    private static void testVolume() {
        System.out.println("Testing Volume...");
        
        // Erstelle 3D-Volume von (0,0,0) bis (2,3,4)
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(2, 3, 4);
        Volume vol = new Volume(p1, p2);
        
        // Teste Dimensionen
        assert vol.dimensions() == 3 : "Volume should have 3 dimensions";
        
        // Teste Volumen: 2 × 3 × 4 = 24
        assert vol.volume() == 24.0 : "Expected 24.0 but got " + vol.volume();
        
        // Teste Kantenlängen
        assert vol.getEdgeLength(0) == 2.0 : "Edge 0 should be 2.0";
        assert vol.getEdgeLength(1) == 3.0 : "Edge 1 should be 3.0";
        assert vol.getEdgeLength(2) == 4.0 : "Edge 2 should be 4.0";
        
        // Ausgabe
        System.out.println("  3D Volume: " + vol);
        System.out.println("  ✓ Volume tests passed\n");
    }

    /**
     * Testet Comparable-Interface.
     * 
     * GETESTET:
     * - compareTo() vergleicht anhand des Volumens
     * - Kleinere Geometrie gibt negativen Wert zurück
     * - Größere Geometrie gibt positiven Wert zurück
     * - Gleiche Geometrie gibt 0 zurück
     */
    private static void testComparable() {
        System.out.println("Testing Comparable...");
        
        // Erstelle zwei Rechtecke unterschiedlicher Größe
        Rectangle r1 = new Rectangle(new Point2D(0, 0), new Point2D(2, 2)); // Fläche: 4
        Rectangle r2 = new Rectangle(new Point2D(0, 0), new Point2D(3, 3)); // Fläche: 9
        
        // r1 < r2
        assert r1.compareTo(r2) < 0 : "r1 should be smaller than r2";
        
        // r2 > r1
        assert r2.compareTo(r1) > 0 : "r2 should be larger than r1";
        
        // r1 == r1
        assert r1.compareTo(r1) == 0 : "r1 should be equal to itself";
        
        // Ausgabe
        System.out.println("  r1 volume: " + r1.volume());
        System.out.println("  r2 volume: " + r2.volume());
        System.out.println("  r1.compareTo(r2): " + r1.compareTo(r2) + " (negative = smaller)");
        System.out.println("  ✓ Comparable tests passed\n");
    }

    /**
     * Testet Encapsulation-Funktionalität.
     * 
     * GETESTET:
     * - 2D: Punkt + Punkt → Rectangle
     * - 2D: Rectangle + Punkt → erweitertes Rectangle
     * - 2D: Rectangle + Rectangle → größeres Rectangle
     * - nD: Punkt + Punkt → Volume
     * - nD: Volume + Punkt → erweitertes Volume
     * - Unterschiedliche Dimensionen → null
     */
    private static void testEncapsulation() {
        System.out.println("Testing Encapsulation...");
        
        // TEST 1: Zwei 2D-Punkte
        System.out.println("  Test 1: Two 2D points → Rectangle");
        Point2D p1 = new Point2D(1, 1);
        Point2D p2 = new Point2D(3, 3);
        Geometry result = p1.encapsulate(p2);
        assert result instanceof Rectangle : "Should create Rectangle";
        assert result.volume() == 4.0 : "Area should be 4.0";
        System.out.println("    Result: " + result);
        
        // TEST 2: Rectangle + Point
        System.out.println("  Test 2: Rectangle + Point → Extended Rectangle");
        Rectangle rect = new Rectangle(new Point2D(0, 0), new Point2D(2, 2));
        Point2D p3 = new Point2D(5, 5);
        result = rect.encapsulate(p3);
        assert result instanceof Rectangle : "Should create Rectangle";
        assert result.volume() == 25.0 : "Area should be 25.0";
        System.out.println("    Result: " + result);
        
        // TEST 3: Rectangle + Rectangle
        System.out.println("  Test 3: Rectangle + Rectangle → Larger Rectangle");
        Rectangle rect2 = new Rectangle(new Point2D(3, 3), new Point2D(6, 6));
        result = rect.encapsulate(rect2);
        assert result instanceof Rectangle : "Should create Rectangle";
        System.out.println("    Result: " + result);
        
        // TEST 4: Zwei 3D-Punkte
        System.out.println("  Test 4: Two 3D points → Volume");
        Point p4 = new Point(0, 0, 0);
        Point p5 = new Point(2, 2, 2);
        result = p4.encapsulate(p5);
        assert result instanceof Volume : "Should create Volume";
        assert result.volume() == 8.0 : "Volume should be 8.0";
        System.out.println("    Result: " + result);
        
        // TEST 5: Volume + Point
        System.out.println("  Test 5: Volume + Point → Extended Volume");
        Volume vol = new Volume(new Point(0, 0, 0), new Point(2, 2, 2));
        Point p6 = new Point(3, 3, 3);
        result = vol.encapsulate(p6);
        assert result instanceof Volume : "Should create Volume";
        assert result.volume() == 27.0 : "Volume should be 27.0";
        System.out.println("    Result: " + result);
        
        // TEST 6: Unterschiedliche Dimensionen
        System.out.println("  Test 6: Different dimensions → null");
        result = p1.encapsulate(p4); // 2D mit 3D
        assert result == null : "Different dimensions should return null";
        System.out.println("    Result: null (correct!)");
        
        System.out.println("  ✓ Encapsulation tests passed\n");
    }
}
