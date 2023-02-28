import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctiiPtTest {


    public static int[] regexFunc1(String str) {

        Pattern p = Pattern.compile( "(-?\\b\\d+)[xX]\\^(-?\\d+\\b)" );
        Matcher m = p.matcher( str );
        int[] vec = new int[20];
        int i = 0, size = 0;

        while (m.find()) {
            size += 2;
            vec[i++] = Integer.parseInt(m.group(1));
            vec[i++] = Integer.parseInt(m.group(2));
        }

        int[] vec1 = new int[size];
        for(int j = 0; j < size; j++)
            vec1[j] = vec[j];

        return vec1;
    }

    public static Polynomial getPolForTest(String s) {

        Polynomial pol1 = new Polynomial();
        int[] vec = regexFunc1(s);

        for(int i = 0; i < vec.length; i+=2)
        {
            int coef = vec[i];
            int putere = vec[i+1];
            Monomial aux = new Monomial(coef, putere);

            pol1.addToPol(aux);
        }

        return pol1;
    }

    public static String toString(Polynomial p)
    {
        String s = new String();
        int i;
        for(i = 0; i < p.getSize() - 1 ; i++) {
            int putere = p.getPolynom().get(i).getPutere();
            int coef = (int)p.getPolynom().get(i).getCoeficient();

            s = s + coef  + "x^" + putere + "+";
        }

        int putere = p.getPolynom().get(i).getPutere();
        int coef = (int)p.getPolynom().get(i).getCoeficient();

        s = s + coef  + "x^" + putere;

        return s;
    }
    public static String toString1(Polynomial p)
    {
        String s = new String();
        int i;
        for(i = 0; i < p.getSize() - 1 ; i++) {
            int putere = p.getPolynom().get(i).getPutere();
            float coef = p.getPolynom().get(i).getCoeficient();

            s = s + coef  + "x^" + putere + "+";
        }

        int putere = p.getPolynom().get(i).getPutere();
        float coef = p.getPolynom().get(i).getCoeficient();

        s = s + coef  + "x^" + putere;

        return s;
    }

    public static String toString2(Polynomial p)
    {
        String s = new String();
        int i;
        for(i = 0; i < p.getSize() - 1 ; i++) {
            int putere = p.getPolynom().get(i).getPutere();
            int coef = (int)p.getPolynom().get(i).getCoeficient();

            s = s + coef  + "x^" + putere + "+";
        }

        int putere = p.getPolynom().get(i).getPutere();
        int coef = (int)p.getPolynom().get(i).getCoeficient();

        s = s + coef  + "x^" + putere + "  Rest: " + (int)Operatii.rest.getPolynom().get(0).getCoeficient()+
                "x^"+ Operatii.rest.getPolynom().get(0).getPutere();

        return s;
    }

}
