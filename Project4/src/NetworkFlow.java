import java.util.*;

public class NetworkFlow {
    public int bottleneck = Integer.MAX_VALUE;
    public int maxFlow = 0;
    //public Stack<Edge> path=new Stack<>();
    public int source;
    public int sink;
    public int numOfNodes;
    public int[] levels;
    public int scaling = 0;
    public ArrayList<Edge> path;
    public int[] shortcut;

    public NetworkFlow(int source, int sink, int numOfNodes) {
        this.sink = sink;
        this.source = source;
        this.numOfNodes = numOfNodes;
        levels = new int[numOfNodes];
        path = new ArrayList<>();
        shortcut=new int[numOfNodes];
    }

    public void addingEdge(List<Edge>[] adjacencies, int capacity, int from, int to) {
        Edge starting = new Edge(capacity, from, to);
        Edge end = new Edge(0, to, from);
        starting.residual = end;
        end.residual = starting;
        adjacencies[from].add(starting);
        adjacencies[to].add(end);
        //scaling = Math.max(scaling, capacity);
        //adjacencies.add(new Edge(capacity,from,to));
        //adjacencies.add(new Edge(0,to,from));
    }


    public int dinicAlgorithm(int numOfNodes,int source,int sink,List<Edge>[] adjacencies){
        Arrays.fill(shortcut,0);
        //System.out.println("Dinic algorithm çalıştı");
        return starter(adjacencies,levels);
    }


    public int starter(List<Edge>[] adjacencies, int[] levels) {
        //System.out.println("starter çalıştı");
        //boolean[] isVisited=new boolean[numOfNodes];
        //Arrays.fill(isVisited,false);

        while (bfs(adjacencies)) {
            Arrays.fill(shortcut,0);
            int d=0;
            do {
                //System.out.println("bfs doğru,döngüye girdi");
                d=dfsForDinic(0,Integer.MAX_VALUE,adjacencies);
                maxFlow +=d;
                //bottleneck=Integer.MAX_VALUE;

                //System.out.println("Max Flow: " + maxFlow);
            }while(d!=0);

            //bottleneck=Integer.MAX_VALUE;
        }
        return maxFlow;
    }


    public boolean bfs(List<Edge>[] adjacencies){
        Arrays.fill(levels,-1);

        Queue<Integer> vertex = new LinkedList<>();
        vertex.offer(source);
        levels[source]=0;



        while(!vertex.isEmpty()){
            //System.out.println("vertex boş değil");
            int n = vertex.poll();

            //System.out.println(adjacencies[n].toString());
            for(Edge e : adjacencies[n]){

                    //System.out.println("komşu bulundu");
                    int cap  =e.remainingCapacity();
                    if(cap>0 && levels[e.to]==-1){
                        levels[e.to]=levels[n]+1;
                        //System.out.println("Vertexe eklendi");
                        vertex.add(e.to);
                    }


            }
        }
        //System.out.println("bfs returned" + String.valueOf(levels[sink]!=-1));
        return levels[sink]!=-1;
    }
    public int dfsForDinic(int currentNode,int currFlow, List<Edge>[] vertices){
        if(currentNode == numOfNodes-1){
            //Arrays.fill(isVisited,false);
            //augmentPath(path,currFlow);
            //isvisited doğru mu yeniden bak
            //System.out.println("returned currFlow : " + currFlow);
            return currFlow;
        }else{

           // isVisited[currentNode]=true;
            //System.out.println("currentNode: " + currentNode);
            final int numOfEdgesForTheVertex=vertices[currentNode].size();
            for(;shortcut[currentNode]<numOfEdgesForTheVertex;shortcut[currentNode]++){
                Edge tmp=vertices[currentNode].get(shortcut[currentNode]);
                //System.out.println("tmp.to : " + tmp.to + "remaniningCapacity " + tmp.remainingCapacity());
                //if(!isVisited[tmp.to]){
                    int capacity=tmp.remainingCapacity();
                    if(levels[tmp.to]==levels[currentNode]+1){
                        if(capacity>0){
                            //System.out.println("dfse gönerilecek sayı : " + Math.min(tmp.remainingCapacity(), currFlow));
                            path.add(tmp);
                            bottleneck=dfsForDinic(tmp.to,Math.min(capacity,currFlow),vertices);
                            //System.out.println("mathminden sonra bottleneck : " + bottleneck);
                            if(tmp.remainingCapacity()==bottleneck){
                                shortcut[currentNode]++;
                            }

                            if(bottleneck>0){
                                //tmp.augmenting(bottleneck);
                                augmentPath(path,bottleneck);
                               // System.out.println("bottleneck returned: " + bottleneck);
                                return bottleneck;
                            }
                        }else{
                            //shortcut[currentNode]++;
                        }
                    }
                //}
        }
            return 0;
    }

    }

    public void augmentPath(ArrayList<Edge> path, int x) {

        for (Edge tmp : path) {
            tmp.augmenting(x);
            tmp.residual.augmenting(x);
        }
        path.clear();
    }
public void dfsWithStack(int currNode,int scalerFactor,List<Edge>[] vertices,boolean[] isVisited){
        Stack<Integer> vertex = new Stack<>();
        vertex.push(currNode);
        while(!vertex.isEmpty()){
            int n = vertex.pop();
            if(!isVisited[n]){
                isVisited[n]=true;

                for(Edge tmp : vertices[n]){
                    if(!isVisited[tmp.to] && tmp.remainingCapacity()>=scalerFactor){
                        vertex.push(tmp.to);
                        path.add(tmp);

                    }
                }
            }
        }
}
    //En doğru olan

    public int dfs(int currentNode, boolean[] isVisited,int currFlow, List<Edge>[] vertices,int scalerFactor){
        if(currentNode == numOfNodes-1){
            Arrays.fill(isVisited,false);
            //augmentPath(path,currFlow);
            //isvisited doğru mu yeniden bak
//System.out.println("returned currFlow : " + currFlow);
            return currFlow;
        }else{

                isVisited[currentNode]=true;
                //System.out.println("currentNode: " + currentNode);
                List<Edge> edgeList=vertices[currentNode];
                for(Edge tmp : edgeList){
                    if(!isVisited[tmp.to]){
                        //System.out.println("tmp.to : " + tmp.to + "remaniningCapacity " + tmp.remainingCapacity());
                        if(scalerFactor<=tmp.remainingCapacity()){
                            //System.out.println("dfse gönerilecek sayı : " + Math.min(tmp.remainingCapacity(), currFlow));
                            path.add(tmp);
                            bottleneck = dfs(tmp.to, isVisited,Math.min(tmp.remainingCapacity(),currFlow),vertices,scalerFactor);
                            //System.out.println("mathminden sonra bottleneck : " + bottleneck);
                            if(bottleneck>0){
                               // System.out.println("Bottleneck to be augmenting " + bottleneck);
                                augmentPath(path,bottleneck);
                                return bottleneck;
                            }
                        }

                        /*
                        if(tmp.remainingCapacity>0 && (levels[currentNode]+1==levels[tmp.to])){
                            int capacity=tmp.remainingCapacity;
                            path.add(tmp);
                            bottleneck = dfs(tmp.to, isVisited,Math.min(capacity,currFlow),vertices);
*/
                        }
                    }
               // System.out.println("return 0a geldi");
            Arrays.fill(isVisited,false);
            path.clear();

        }
        return 0;
        }

    /*
    public int dfs(int i, List<Edge>[] vertices, boolean[] isVisited,int[] levels,int flow){
        if(i==sink){
            System.out.println("sinke ulaşıldı");
            augmentPath(path,flow);
            return flow;
        }
        isVisited[i]=true;
        List<Edge> adjacent = vertices[i];
        for(Edge tmp : adjacent){
            if(!isVisited[tmp.to]){
                if(tmp.remainingCapacity>0 && (levels[i]+1==levels[tmp.to])){
                    if(tmp.remainingCapacity<bottleneck){
                        bottleneck=tmp.remainingCapacity;
                    }
                    path.add(tmp);
                    return dfs(tmp.to,vertices,isVisited,levels, Math.min(bottleneck,tmp.remainingCapacity));
                }
            }

        }
    return 0;
    }
    */
    public void makeAllUnvisited(boolean[] isVisited) {
        for (boolean b : isVisited) {
            b = false;
        }
    }

    public int dfsRunner(int source, int sink, List<Edge>[] vertices) {
        int scalerFactor = (int) Math.pow(2, (int) (Math.floor((Math.log(scaling) / Math.log(2)))));
        //boolean[] isVisited = new boolean[numOfNodes];
        //Arrays.fill(isVisited, false);
        for (int i = 5; scalerFactor > 0; scalerFactor = scalerFactor / 2) {
            do {
                //System.out.println("Max flow before " + scalerFactor + ": " + maxFlow);
                boolean[] isVisited = new boolean[numOfNodes];
                Arrays.fill(isVisited,false);
                i=dfs(source,isVisited,Integer.MAX_VALUE,vertices,scalerFactor);
                //makeAllUnvisited(isVisited);
                //System.out.println("bottleneck: " + i);
                maxFlow+=i;



            } while (i != 0);
            for(Edge tmp : vertices[sink]){
                //System.out.println(tmp.capacity - tmp.remainingCapacity);
            }


        }
return maxFlow;
    }
}