import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class OperatiiTest {

    @Test
    @DisplayName("Testarea adunarii a doua polinoame: ")
    public void addTest(){
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();

        String s1 = "3x^2+5x^1+3x^0";
        String s2 = "7x^2+2x^1";

        p1 = FunctiiPtTest.getPolForTest(s1);
        p2 = FunctiiPtTest.getPolForTest(s2);

        assertEquals("10x^2+7x^1+3x^0", FunctiiPtTest.toString(Operatii.addPolynomials(p1, p2)));

    }

    @Test
    @DisplayName("Testarea scaderii a doua polinoame: ")
    public void subtractTest(){
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();

        String s1 = "3x^2+5x^1+3x^0";
        String s2 = "7x^2+2x^1";

        p1 = FunctiiPtTest.getPolForTest(s1);
        p2 = FunctiiPtTest.getPolForTest(s2);

        assertEquals("-4x^2+3x^1+3x^0", FunctiiPtTest.toString(Operatii.subtractPolynomials(p1, p2)));
    }

    @Test
    @DisplayName("Testarea inmultirii a doua polinoame: ")
    public void multiplyTest(){
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();

        String s1 = "3x^2+5x^1";
        String s2 = "7x^2+2x^1";

        p1 = FunctiiPtTest.getPolForTest(s1);
        p2 = FunctiiPtTest.getPolForTest(s2);

        assertEquals("21x^4+6x^3+35x^3+10x^2", FunctiiPtTest.toString(Operatii.multiplyPolynomials(p1, p2)));
    }


    @Test
    @DisplayName("Testarea integrarii a unui polinom: ")
    public void integrationTest(){
        Polynomial p1 = new Polynomial();

        String s1 = "3x^2+5x^1";

        p1 = FunctiiPtTest.getPolForTest(s1);

        assertEquals("1.0x^3+2.5x^2", FunctiiPtTest.toString1(Operatii.integrationPolynomial(p1)));
    }

    @Test
    @DisplayName("Testarea derivarii unui polinom: ")
    public void derivationTest(){
        Polynomial p1 = new Polynomial();

        String s1 = "3x^2+5x^1";

        p1 = FunctiiPtTest.getPolForTest(s1);

        assertEquals("6x^1+5x^0", FunctiiPtTest.toString(Operatii.derivativePolynomial(p1)));
    }

    @Test
    @DisplayName("Testarea impartirii a doua polinoame: ")
    public void divisionTest(){
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();

        String s1 = "1x^2+5x^1+6x^0";
        String s2 = "1x^1+2x^0";

        p1 = FunctiiPtTest.getPolForTest(s1);
        p2 = FunctiiPtTest.getPolForTest(s2);

        assertEquals("1x^1+3x^0  Rest: 0x^0", FunctiiPtTest.toString2(Operatii.divisionPolynomial(p1, p2)));
    }

}