
public class Monomial {
    private float coeficient;
    private int putere;


    public Monomial(float coef, int putere)
    {
        this.coeficient = coef;
        this.setPutere(putere);
    }


    public float getCoeficient() {
        return coeficient;
    }

    public void setCoeficient(float number) {
        this.coeficient = number;
    }

    public int getPutere() {
        return putere;
    }

    public void setPutere(int putere) {
        this.putere = putere;
    }


}
