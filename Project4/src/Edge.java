

public class Edge {
public int flow;
public int capacity;
public int remainingCapacity;
public int from;
public int to;
public Edge residual;

    public Edge(int capacity, int from, int to) {
        this.capacity = capacity;
        this.from = from;
        this.to = to;
        this.remainingCapacity=capacity;
    }
    public boolean isResudial(){
        return capacity==0;
    }
    public int remainingCapacity(){
        return this.capacity-this.flow;
    }
    public void augmenting(int bottleneck){
        if (isResudial()) {
            flow -= bottleneck;
        } else {
            flow += bottleneck;
        }
        //sonradan eklediğim:

    }

    @Override
    public String toString() {
        return "Edge{" +
                "capacity=" + capacity +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
