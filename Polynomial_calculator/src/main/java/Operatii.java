
public class Operatii {

    public static int Max(int a, int b) {
        int max;
        if(a>b) max = a;
        else max = b;
        return max;
    }

    public static Polynomial addPolynomials(Polynomial p1,Polynomial p2) {
        Polynomial pol = new Polynomial();
        int size1 = p1.getPolynom().get(0).getPutere();
        int size2 = p2.getPolynom().get(0).getPutere();

        int size = Max(size1,size2) + 1;
        int[] sum =  new int[size];


        for(Monomial a: p1.getPolynom()) {
            int putere = a.getPutere();
            int coeficient = (int)a.getCoeficient();

            sum[putere] = sum[putere] + coeficient;
        }

        for(Monomial b: p2.getPolynom()) {
            int putere = b.getPutere();
            int coeficient = (int)b.getCoeficient();

            sum[putere] += coeficient;
        }

        for(int i = size - 1; i >= 0; i--) {
            float coef = sum[i];
            int putere = i;
            Monomial aux = new Monomial(coef, putere);
            pol.addToPol(aux);
        }
        return pol;
    }





    public static Polynomial subtractPolynomials(Polynomial p1,Polynomial p2) {

        Polynomial pol = new Polynomial();
        int size1 = p1.getPolynom().get(0).getPutere();
        int size2 = p2.getPolynom().get(0).getPutere();

        int size = Max(size1,size2) + 1;
        int[] sum =  new int[size];

        for(Monomial a: p1.getPolynom()) {
            int putere = a.getPutere();
            int coeficient = (int)a.getCoeficient();

            sum[putere] = sum[putere] + coeficient;
        }

        for(Monomial b: p2.getPolynom()) {
            int putere = b.getPutere();
            int coeficient = (int)b.getCoeficient();

            sum[putere] -= coeficient;
        }

        for(int i = size-1; i >= 0; i--) {
            float coef = sum[i];
            int putere = i;
            Monomial aux = new Monomial(coef, putere);
            pol.addToPol(aux);
        }
        return pol;

    }


    public static Polynomial multiplyPolynomials(Polynomial p1, Polynomial p2) {
        Polynomial pol = new Polynomial();


        for(Monomial a: p1.getPolynom()) {
            float coeficient1 = a.getCoeficient();
            int putere1 = a.getPutere();

            for(Monomial b: p2.getPolynom())
            {
                float coeficient2 = b.getCoeficient();
                int putere2 = b.getPutere();

                Monomial aux = new Monomial(coeficient1*coeficient2,putere1+putere2);

                pol.addToPol(aux);
            }
        }


        return pol;
    }

    public static Polynomial derivativePolynomial(Polynomial p) {
        Polynomial p2 = new Polynomial();

        for(Monomial b: p.getPolynom()) {
            float coef = b.getCoeficient();
            int putere = b.getPutere();
            float coef2 = coef*putere;
            int putere2 = putere - 1;
            Monomial aux = new Monomial(coef2, putere2);

            p2.addToPol(aux);
        }

        return p2;
    }


    public static Polynomial integrationPolynomial(Polynomial p) {
        Polynomial p2 = new Polynomial();

        for(Monomial b: p.getPolynom()) {
            float coef = b.getCoeficient();
            int putere = b.getPutere();

            float coef2 =  coef/(1+putere);
            int putere2 = putere + 1;
            Monomial aux = new Monomial(coef2, putere2);

            p2.addToPol(aux);
        }

        return p2;
    }

    public static Monomial firstMon(Polynomial p1, Polynomial p2){
        int coef1 = (int)p1.getPolynom().get(0).getCoeficient();
        int putere1 = p1.getPolynom().get(0).getPutere();
        int coef2 = (int)p2.getPolynom().get(0).getCoeficient();
        int putere2 = p2.getPolynom().get(0).getPutere();

        return new Monomial(coef1/coef2, putere1 - putere2);
    }

    public static Polynomial  rest;

    public static Polynomial divisionPolynomial(Polynomial p1, Polynomial p2){
        int putere1 = p1.getPolynom().get(0).getPutere();
        int putere2 = p2.getPolynom().get(0).getPutere();

        Polynomial result = new Polynomial(); int i = 0;
        while(p1.getPolynom().get(0).getPutere() >= p2.getPolynom().get(0).getPutere() &&
                p1.getPolynom().get(0).getPutere() != 0) {
            Monomial aux = firstMon(p1, p2);
            result.addToPol(aux);

            Polynomial toMultiply = new Polynomial();
            toMultiply.addToPol(aux);

            Polynomial aux1 = new Polynomial();
            aux1 = multiplyPolynomials(toMultiply, p2);

            Polynomial aux2 = new Polynomial();
            aux2 = subtractPolynomials(p1, aux1);
            p1 = aux2;

            int coef = (int)p1.getPolynom().get(0).getCoeficient();
            int putere = p1.getPolynom().get(0).getPutere();
            if(p1.getPolynom().get(0).getCoeficient() == 0){
                p1.getPolynom().remove(0);
            }
             rest = p1;
        }

        return result;
    }

}


