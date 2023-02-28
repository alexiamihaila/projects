
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

        private Polynomial p_model;
        private View p_view;

        public Controller(Polynomial p_model, View p_view){
            this.p_model = p_model;
            this.p_view = p_view;

            this.p_view.addListener(new AddListener());
            this.p_view.subtractListener(new SubtractListener());
            this.p_view.multiplicationListener(new MultiplicationListener());
            this.p_view.derivativeListener(new DerivativeListener());
            this.p_view.integrationListener(new IntegrationListener());
            this.p_view.divisionListener(new DivisionListener());
        }


        public int[] regexFunc(String str) {

            Pattern p = Pattern.compile( "(-?\\b\\d+)[xX]\\^(-?\\d+\\b)" );
            Matcher m = p.matcher( str );
            int[] vec = new int[20];
            int i = 0, size = 0;

            while (m.find()) {
                    size += 2;
                    vec[i++] = Integer.parseInt(m.group(1));
                    vec[i++] = Integer.parseInt(m.group(2));
            }

            if(!m.find())
                p_view.getTfRes().setText("Datele introduse nu au fost corecte");

            int[] vec1 = new int[size];
            for(int j = 0; j < size; j++)
                    vec1[j] = vec[j];

            return vec1;
        }

        public Polynomial getPol1() {

            Polynomial pol1 = new Polynomial();

            String str = p_view.getTfPol1();

            int[] vec = regexFunc(str);


            for(int i = 0; i < vec.length; i+=2)
            {
                int coef = vec[i];
                int putere = vec[i+1];
                Monomial aux = new Monomial(coef, putere);

                pol1.addToPol(aux);
            }

            return pol1;

        }


        public Polynomial getPol2() {

            Polynomial pol1 = new Polynomial();

            String str = p_view.getTfPol2();

            int[] vec = regexFunc(str);


            for(int i = 0; i < vec.length; i+=2)
            {
                int coef = vec[i];
                int putere = vec[i+1];
                Monomial aux = new Monomial(coef, putere);

                pol1.addToPol(aux);
            }

            return pol1;

        }


        public void setTfResult(Polynomial p)
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

            p_view.getTfRes().setText(s);
        }

        public void setTfResult1(Polynomial p)
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

            p_view.getTfRes().setText(s);
        }

    public void setTfResult2(Polynomial p)
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

        p_view.getTfRes().setText(s);
    }




        public class AddListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                Polynomial p1 = new Polynomial();
                Polynomial p2 = new Polynomial();
                Polynomial result = new Polynomial();

                p1 = getPol1();
                p2 = getPol2();


                result = Operatii.addPolynomials(p1, p2);

                setTfResult(result);
            }

        }

        public class SubtractListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynomial p1 = new Polynomial();
                Polynomial p2 = new Polynomial();
                Polynomial result = new Polynomial();

                p1 = getPol1();
                p2 = getPol2();


                result = Operatii.subtractPolynomials(p1, p2);

                setTfResult(result);
            }
        }

        public class MultiplicationListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynomial p1 = new Polynomial();
                Polynomial p2 = new Polynomial();
                Polynomial result = new Polynomial();

                p1 = getPol1();
                p2 = getPol2();


                result = Operatii.multiplyPolynomials(p1, p2);

                setTfResult(result);
            }
        }

        public class DerivativeListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynomial p1 = new Polynomial();
                Polynomial result = new Polynomial();
                p1 = getPol1();
                result = Operatii.derivativePolynomial(p1);

                setTfResult(result);
            }
        }

        public class IntegrationListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynomial p1 = new Polynomial();
                Polynomial result = new Polynomial();
                p1 = getPol1();
                result = Operatii.integrationPolynomial(p1);

                setTfResult1(result);
            }
        }

    public class DivisionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Polynomial p1 = new Polynomial();
            Polynomial p2 = new Polynomial();
            Polynomial result = new Polynomial();
            p1 = getPol1();
            p2 = getPol2();
            result = Operatii.divisionPolynomial(p1,p2);

            setTfResult2(result);

        }
    }
    }


