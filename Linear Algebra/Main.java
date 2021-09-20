public class Main {
    
    public static void main(String[] args) {

        Vector y = new Vector(6,3,3,-1);

        Subspace W = new Subspace(
            new Vector(1,1,0,1),
            new Vector(-1,5,3,-4),
            new Vector(-1,0,1,1)
        );

        Vector y_ = y.clone().ProjectOn(W);

        y_.print();

        y.Subtract(y_);

        y.print();



    }

}
