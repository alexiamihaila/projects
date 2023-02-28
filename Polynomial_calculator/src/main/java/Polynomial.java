
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
        private ArrayList<Monomial> polynom;


        public Polynomial() {
            this.polynom = new ArrayList<Monomial>();
        }

        public ArrayList<Monomial> getPolynom() {
            return polynom;
        }

        public void setPolynom(ArrayList<Monomial> polynom) {
            this.polynom = polynom;
        }


        public int getSize() {
            return polynom.size();
        }


        public void addToPol(Monomial e) {
            polynom.add(e);
        }

        public void removeFromPol(Monomial e) {
            polynom.remove(e);
        }

}


