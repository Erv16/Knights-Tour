/*
    Simple position class used to store previously visited positions on board. Weight variable is used to
    determine next move with Warnsdorff
 */
public class Position {
    private int x_cor;
    private int y_cor;
    private int weight;

    public Position() {

    }

    public Position(int x_cor, int y_cor) {

        this.x_cor = x_cor;
        this.y_cor = y_cor;
    }

    public int getX() {
        return x_cor;
    }

    public int getY() {
        return y_cor;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "X: "+x_cor+" Y: "+y_cor;
    }
}