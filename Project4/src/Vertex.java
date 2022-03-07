public class Vertex {
    public int height;
    public int excessFlow;
    public int id;

    public Vertex(int id) {
        this.id = id;
        this.excessFlow=0;
        this.height=0;
    }
}
