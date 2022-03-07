import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import static java.lang.System.out;

public class project4main {

    public static void main(String[] args)throws IOException {
	// write your code here
        String inputFileName = args[0];
        File myInputFile = new File(inputFileName);
        BufferedReader bufferedReader;
        int greenRegionTrain = 0;
        ArrayList<Integer> greenRegionTrainCap = new ArrayList<>();
        ArrayList<Integer> redRegionTrainCap = new ArrayList<>();
        ArrayList<Integer> greenRegionReindeerCap = new ArrayList<>();
        ArrayList<Integer> redRegionReindeerCap = new ArrayList<>();
        Map<String,Integer> bagTypes = new LinkedHashMap<>();
        int redRegionTrain = 0;
        int greenRegionReindeer = 0;
        int redRegionReindeer = 0;
        int numOfBags = 0;
        //String greenRegionTrainCapacities = "";
        //String redRegionTrainCapacities = "";
        //String greenRegionReindeerCapacities = "";
        //String redRegionReindeerCapacities = "";
        String bags = "";
        List<Edge>[] vertices;
        int largestEdgeSize=0;
        int scaler=0;
        int maxFlow=0;
        int totalFlow=0;

        try{
            //bufferedReader = new BufferedReader(new FileReader(myInputFile));
            Scanner sc = new Scanner(myInputFile);
            sc.useLocale(Locale.US);
            String line = "";



//Integer.parseInt(line.substring(0,1));
            //line = bufferedReader.readLine();
            if(sc.hasNextInt())
                greenRegionTrain=sc.nextInt();

            //System.out.println("GreenregionTrain sayısı . " + greenRegionTrain);
            int a=0;
            int zeroCounter=0;
            if(greenRegionTrain>0){
                for(int i=0;i<greenRegionTrain && sc.hasNextInt();i++){
                    a=sc.nextInt();
                    //out.println(a);
                    //System.out.println("Capacity of greenRegionTrain" + a);
                    greenRegionTrainCap.add(a);
                    /*
                    if(a!=0){

                    }else{
                        zeroCounter++;
                    }
*/
                    //if(a>largestEdgeSize)
                    //largestEdgeSize=a;
                }
            }


            //System.out.println(greenRegionTrainCap.toString());

            /*
            line = bufferedReader.readLine();
            if(!line.equalsIgnoreCase(""))
                greenRegionTrainCapacities=line;
*/

            zeroCounter=0;
            if(sc.hasNextInt())
                redRegionTrain=sc.nextInt();
            //System.out.println("RedregionTrain sayısı . " + redRegionTrain);

            for(int i=0;i<redRegionTrain && sc.hasNextInt();i++){
                a=sc.nextInt();
                //System.out.println(a);
                redRegionTrainCap.add(a);


                //if(a>largestEdgeSize)
                    //largestEdgeSize=a;
            }



            if(sc.hasNextInt())
                greenRegionReindeer=sc.nextInt();
            //System.out.println("GreenRegionReindeer sayısı : " + greenRegionReindeer);
            for(int i=0;i<greenRegionReindeer && sc.hasNextInt();i++){
                a=sc.nextInt();

                    greenRegionReindeerCap.add(a);




                //if(a>largestEdgeSize)
                    //largestEdgeSize=a;
            }


            if(sc.hasNextInt())
                redRegionReindeer=sc.nextInt();
           // System.out.println("RedRegionReindeer sayısı ->" + redRegionReindeer);

            for(int i=0;i<redRegionReindeer && sc.hasNextInt();i++){
                a=sc.nextInt();

                    redRegionReindeerCap.add(a);


                //if(a>largestEdgeSize)
                    //largestEdgeSize=a;
            }


            if(sc.hasNextInt())
                numOfBags=sc.nextInt();


            int f=1;
            while(sc.hasNext()){
                String type = sc.next();
                if(sc.hasNextInt()) {
                    int cap = sc.nextInt();
                    totalFlow+=cap;
                    if (cap != 0) {
                        if (bagTypes.containsKey(type)) {
                           // bagTypes.put(type, cap);
                            //bagTypes.put(type + f, cap);
                           //f++;

                            if (!type.contains("a")) {
                                cap += bagTypes.get(type);
                                bagTypes.replace(type, cap);
                                numOfBags--;
                            } else {
                                bagTypes.put(type + String.valueOf(f), cap);
                                f++;
                            }


                        } else {
                            bagTypes.put(type, cap);
                        }

                        //if(cap>largestEdgeSize)
                        //largestEdgeSize=cap;

                       // System.out.println(type + " " + bagTypes.get(type));

                    }
                }
            }
            //numOfBags-=zeroCounter;

            zeroCounter=0;
            //scaler = (int) Math.pow(2,(int)Math.floor((Math.log(largestEdgeSize))/(Math.log(2))));

            int totalVertices=numOfBags + redRegionReindeerCap.size() + greenRegionReindeerCap.size() + redRegionTrainCap.size() + greenRegionTrainCap.size() + 2;
            //System.out.println("Total vertices: " + totalVertices);
            NetworkFlow maxflow = new NetworkFlow(0,totalVertices-1,totalVertices);
            vertices = new List[totalVertices];
            for(int i=0;i<vertices.length;i++){
                vertices[i] = new ArrayList<Edge>();
            }
            int count=1;
            for(String s : bagTypes.keySet()){
                if(s.contains("a")){
                    maxflow.addingEdge(vertices,bagTypes.get(s),0,count);
                }else{
                    maxflow.addingEdge(vertices,Integer.MAX_VALUE,0,count);
                }

                //vertices[0].add(new Edge(bagTypes.get(s),0,count));
                //vertices[0].add(new Edge(0,count,0));
                count++;
            }
            boolean[]marked=new boolean[totalVertices];
            int[]excess=new int[totalVertices];
            Arrays.fill(marked,false);
            int totalExcess=0;
            count=1;
            for(String s: bagTypes.keySet()) {
                totalExcess=0;
                if(s.contains("abd")){
                    for(int i=0;i<greenRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+1+i);
                        totalExcess+=1;
                    }
                    if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);

                    }
                    count++;
                    continue;
                }
                //değiştirmeden önce
                /*
                if(s.equalsIgnoreCase("abd")){
                    for(int i=0;i<greenRegionTrain;i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+1+i);
                    }
                }
                */
                else if(s.contains("abe")){
                    for(int i=0;i< greenRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+redRegionTrain+greenRegionTrain+1+i);
                       // out.println("Edge added  " + count + "  " +numOfBags+redRegionTrain+greenRegionTrain+1+i);
                    totalExcess+=1;
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.contains("acd")){
                    for(int i=0;i< redRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+greenRegionTrain+1+i);
                       // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    totalExcess+=1;
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.contains("ace")){
                    for(int i=0;i< redRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+greenRegionTrain+redRegionTrain+greenRegionReindeer+1+i);
                       // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                   totalExcess+=1;
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.contains("ab")){
                    for(int i=0;i< greenRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+1+i);
                       // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    totalExcess+=1;
                    }

                    for(int i=0;i< greenRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+redRegionTrain+greenRegionTrain+1+i);
                       // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    totalExcess=+1;
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.contains("ac")){
                    for(int i=0;i< redRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+greenRegionTrain+1+i);
                       totalExcess+=1;
                        // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i< redRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+greenRegionTrain+redRegionTrain+greenRegionReindeer+1+i);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    totalExcess+=1;
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.contains("ad")){
                    for(int i=0;i< greenRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+1+i);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                        //vertices[count].add(new Edge(1,count,numOfBags+1+i));
                        //vertices[count].add(new Edge(0,numOfBags+1+i,count));
                        totalExcess+=1;
                    }for(int i=0;i< redRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,1,count, numOfBags+greenRegionTrain+1+i);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    totalExcess+=1;
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.contains("ae")){
                for(int i=0;i< redRegionReindeerCap.size();i++){
                    maxflow.addingEdge(vertices,1,count,numOfBags+greenRegionTrain+redRegionTrain+greenRegionReindeer+1+i);
                   totalExcess+=1;
                    // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                }for(int i=0;i< greenRegionReindeerCap.size();i++){
                maxflow.addingEdge(vertices,1,count,numOfBags+greenRegionTrain+redRegionTrain+1+i);
                       // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                totalExcess+=1;
                }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                count++;
                    continue;
                }else if(s.contains("a")){
                    for(int i=0;i< redRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+greenRegionTrain+redRegionTrain+greenRegionReindeer+1+i);
                       // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    totalExcess+=1;
                    }for(int i=0;i< greenRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+greenRegionTrain+redRegionTrain+1+i);
                       totalExcess+=1;
                        // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i< greenRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,1,count,numOfBags+1+i);
                       totalExcess+=1;
                        // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i< redRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,1,count, numOfBags+greenRegionTrain+1+i);
                        totalExcess+=1;
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.equalsIgnoreCase("bd")){
                    for(int i=0;i<greenRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices, bagTypes.get(s), count,numOfBags+1+i);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    totalExcess+=bagTypes.get(s);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.equalsIgnoreCase("be")){
                    for(int i=0;i< greenRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices, bagTypes.get(s), count,numOfBags+greenRegionTrain+redRegionTrain+1+i);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    totalExcess+=bagTypes.get(s);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.equalsIgnoreCase("b")){
                    for(int i=0;i< greenRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,numOfBags+greenRegionTrain+redRegionTrain+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i< greenRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,bagTypes.keySet().size()+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.equalsIgnoreCase("cd")){
                    for(int i=0;i< redRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count, bagTypes.keySet().size()+greenRegionTrain+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.equalsIgnoreCase("ce")){
                    for(int i=0;i< redRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,numOfBags+greenRegionTrain+redRegionTrain+greenRegionReindeer+1+i);
                       totalExcess+=bagTypes.get(s);
                        // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.equalsIgnoreCase("c")){
                    for(int i=0;i< redRegionReindeerCap.size();i++){
                        //changed part
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,bagTypes.keySet().size()+greenRegionTrain+redRegionTrain+greenRegionReindeer+1+i);
                       totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i< redRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count, bagTypes.keySet().size()+greenRegionTrain+1+i);
                       totalExcess+=bagTypes.get(s);
                        // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.equalsIgnoreCase("d")){
                    for(int i=0;i< greenRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,bagTypes.keySet().size()+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i< redRegionTrainCap.size();i++) {
                        maxflow.addingEdge(vertices, bagTypes.get(s), count, bagTypes.keySet().size() + greenRegionTrain + 1 + i);
                       totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else if(s.equalsIgnoreCase("e")){
                    for(int i=0;i< redRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,bagTypes.keySet().size()+ greenRegionTrainCap.size()+ redRegionTrainCap.size()+ greenRegionReindeerCap.size()+1+i);
                       totalExcess+=bagTypes.get(s);
                        // out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i< greenRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,bagTypes.keySet().size()+ greenRegionTrainCap.size()+ redRegionTrainCap.size()+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }else{
                    for(int i=0;i<redRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,bagTypes.keySet().size()+greenRegionTrain+redRegionTrain+greenRegionReindeer+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i<greenRegionReindeerCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,bagTypes.keySet().size()+greenRegionTrain+redRegionTrain+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i<redRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count, bagTypes.keySet().size()+greenRegionTrain+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }for(int i=0;i<greenRegionTrainCap.size();i++){
                        maxflow.addingEdge(vertices,bagTypes.get(s),count,bagTypes.keySet().size()+1+i);
                        totalExcess+=bagTypes.get(s);
                        //out.println("Edge added  " + count + "  " +numOfBags+greenRegionTrain+1+i);
                    }if(totalExcess>bagTypes.get(s)){
                        marked[count]=true;
                        excess[count]=bagTypes.get(s);
                    }
                    count++;
                    continue;
                }
            }


            count=numOfBags+1;
            int totalNumOfVehicles=greenRegionReindeer+greenRegionTrain+redRegionReindeer+redRegionTrain;
            for(int i=0;i<greenRegionTrainCap.size();i++){
                maxflow.addingEdge(vertices,greenRegionTrainCap.get(i),count,totalVertices-1);
                count++;
            }for(int i=0;i<redRegionTrainCap.size();i++){
                maxflow.addingEdge(vertices,redRegionTrainCap.get(i),count,totalVertices-1);
                count++;
            }for(int i=0;i<greenRegionReindeerCap.size();i++){
                maxflow.addingEdge(vertices,greenRegionReindeerCap.get(i),count,totalVertices-1);
                count++;
            }for(int i=0;i<redRegionReindeerCap.size();i++){
                maxflow.addingEdge(vertices,redRegionReindeerCap.get(i),count,totalVertices-1);
                count++;
            }
/*
            for(int t=0;t<vertices.length;t++) {
                for (int i = 0; i < vertices[t].size(); i++) {
                    //System.out.println("Çalıştı");
                    System.out.println(vertices[t].get(i).toString());
                }
                out.println();
            }
            */
            //maxFlow = maxflow.dfsRunner(0,totalVertices-1,vertices);
            maxFlow=maxflow.dinicAlgorithm(totalVertices,0,totalVertices-1,vertices);
            System.out.println("Max flow: " + maxFlow);
            out.println("minmimum possible : " + (totalFlow-maxFlow));
            /*
            for(int i=0;i<totalNumOfVehicles;i++){
                if(i<greenRegionTrain){
                    maxflow.addingEdge(vertices,greenRegionTrainCap.get(i),count,totalVertices-1);
                    //vertices[count].add(new Edge(greenRegionTrainCap.get(i),count,totalVertices-1));
                }else if(i<redRegionTrain && i>=greenRegionTrain){
                    //vertices[count].add(new Edge(redRegionTrainCap.get(i-greenRegionTrain),count,totalVertices-1));
                    maxflow.addingEdge(vertices,greenRegionTrainCap.get(i-greenRegionTrain),count,totalVertices-1);
                }else if(i<greenRegionReindeer && i>=redRegionTrain){
                    maxflow.addingEdge(vertices,greenRegionReindeerCap.get(i-greenRegionTrain-redRegionTrain),count,totalVertices-1);
                }else{
                    maxflow.addingEdge(vertices,redRegionReindeerCap.get(i-greenRegionTrain-redRegionTrain-greenRegionReindeer),count,totalVertices-1);
                }
                //vertices[count].add(new Edge(0,totalVertices-1,count));
                count++;
            }
*/
        }catch (FileNotFoundException e){
            out.println("Catch - An error occurred.");
            e.printStackTrace();
        }
        String outputFileName = args[1];
        File myOutputFile = new File(outputFileName);
        try{
            BufferedWriter bfWriter = new BufferedWriter(new FileWriter(outputFileName));

            //out.println("filewritera geldi");
            //bfWriter.write("ekşi");
            bfWriter.write(String.valueOf(totalFlow-maxFlow));
            //bfWriter.write(totalFlow-maxFlow);
            bfWriter.close();

        }catch (IOException e) {
            out.println("Catch - An error occurred.");
            e.printStackTrace();
        }
    }

}
