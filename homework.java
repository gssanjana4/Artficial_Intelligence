import java.util.*;
import java.io.*;
import java.lang.*;
public class homework {
static int rowNum[] = {-1, 0, 0, 1, -1, 1, -1, 1}; 
static int colNum[] = {0, -1, 1, 0, -1, -1, 1, 1}; 
	static boolean checkTarget(BFSNode src, List<BFSNode> targets) {
		for(BFSNode i:targets) {
		    if(src.equals(i)) {
		    	return true;
		    }
		}
		return false;
		
	}
	static long findw(BFSNode currNode, BFSNode adjNode) {
		// TODO Auto-generated method stub
		if(Math.abs(currNode.getX()-adjNode.getX())==1 && Math.abs(currNode.getY()-adjNode.getY())==1){
			return 14;
			
		}
		return 10;
		
	}
	static boolean isvalid(long w, long h, long dx, long dy)
	{
        return (dx >= 0) && (dx < h) && (dy >=0) && (dy < w);
	}
	
	static void BFS(long W, long H, ArrayList<Long> src, List<BFSNode> targets, long Z, 
			List<List<Long>> hmap) throws IOException
	{
		File outputfile = new File("output.txt");
		FileWriter writer = new FileWriter(outputfile);
		Map<BFSNode, BFSNode> parentMap = new HashMap<>();
		boolean[][] visited = new boolean[(int)H][(int)W];
		
        LinkedList<BFSNode> queue = new LinkedList<BFSNode>();
        BFSNode _src = new BFSNode(src.get(1), src.get(0));
        queue.add(_src);
        visited[(int)_src.getX()][(int)_src.getY()] = true;
        parentMap.put(_src, _src);
        long n = 0;
        int cnt = 0;
        int trg = 0;
        while(!queue.isEmpty() && (targets.size() - n) > 0) {
        	BFSNode currNode = queue.poll();	
            if(checkTarget(currNode, targets)) {
            	n++;
            	trg++;
            }
            //directions and conditions
            for(int i=0;i<8;i++)
            {
            	long row = currNode.getX() + rowNum[i];
            	long col = currNode.getY()+colNum[i];
                if ((isvalid(W,H,row,col) && !visited[(int)row][(int)col]) && Math.abs(hmap.get((int)row).get((int)col)-
                		hmap.get((int)currNode.getX()).get((int)currNode.getY()))<=Z) 
                {
                	cnt++;
                	BFSNode tmp = new BFSNode(row, col);
                	queue.add(tmp);
                	visited[(int)row][(int)col] =true;
                	parentMap.put(tmp, currNode);
                	
                }

            }
           
        }
        for(int i =0;i< targets.size(); i++) {
        	Stack<BFSNode> s = new Stack<BFSNode>();
        	BFSNode t = targets.get(i);
        	s.push(t);
        	
        	if(parentMap.get(t)==null) {
        		if(i<targets.size()-1)
        			writer.write("FAIL\n");
        		else writer.write("FAIL");
        		continue;
        	}
            while(!t.equals(parentMap.get(t))) {
            	s.push(parentMap.get(t));
            	t = parentMap.get(t);
            }
          while(!s.isEmpty()) {
        		BFSNode tmp = s.pop();
        	writer.write(tmp.getY()+","+tmp.getX() + " ");
        	}
          if(i<targets.size()-1)
  			writer.write("\n");
        	
        }    
        writer.close();
		
	}
	
	
	static void UCS(long W, long H,ArrayList<Long> src, List<BFSNode> targets, long Z, List<List<Long>> hmap) throws IOException
	{
		File outputfile = new File("output.txt");
		FileWriter writer = new FileWriter(outputfile);
		PriorityQueue<UCSNode> pQueue= new PriorityQueue<UCSNode>();
		Map<BFSNode, BFSNode> path= new HashMap<>();
		Map<BFSNode, Long> distance = new HashMap<>();
		long costweight=0;
		UCSNode ucs= new UCSNode(0,src.get(1),src.get(0));
		BFSNode nsrc = new BFSNode(src.get(1),src.get(0));
		Long Nooftargets=(long) 0;
		pQueue.add(ucs);
		distance.put(nsrc,(long) 0);
		path.put(nsrc,nsrc);
		while(!pQueue.isEmpty())
		{
			UCSNode topobj= pQueue.poll();
			BFSNode currNode = new BFSNode(topobj.getX(),topobj.getY());
			if(checkTarget(currNode, targets)) {
            	Nooftargets++;
            	if(Nooftargets==targets.size()) {
            		break;
            	}
            }

			 for(int i=0;i<8;i++)
	            {
	            	long row = currNode.getX() + rowNum[i];
	            	long col = currNode.getY()+colNum[i];
	             	
	            	BFSNode adjNode = new BFSNode(row,col);
	            	
	            	costweight = findw(currNode,adjNode);
	            	long distadj = 0;
	            	long distcurr = 0;
	            	if(distance.get(adjNode)!=null) {
	            		distadj = distance.get(adjNode);
	            		distcurr = distance.get(currNode);
	            	}
	           	
	            	if ((isvalid(W,H,row,col) && distance.get(adjNode)==null && Math.abs(hmap.get((int)row).get((int)col)-hmap.get((int)currNode.getX()).get((int)currNode.getY()))<=Z)){
	            		distance.put(adjNode, distance.get(currNode)+costweight);
	            		path.put(adjNode, currNode);
	            		UCSNode ucs1= new UCSNode(distance.get(adjNode),row,col);
	            		pQueue.add(ucs1);
	            			            		
	            	}
	            	else if ((isvalid(W,H,row,col) && distadj>distcurr+costweight && Math.abs(hmap.get((int)row).get((int)col)-hmap.get((int)currNode.getX()).get((int)currNode.getY()))<=Z)){
	            		distance.put(adjNode, distance.get(currNode)+costweight);
	            		path.put(adjNode, currNode);

	            		UCSNode ucs1= new UCSNode(distance.get(adjNode),row,col);
	            		pQueue.add(ucs1);
	            	}

	            }
}
		
		for(int i =0;i< targets.size(); i++) {
        	Stack<BFSNode> s = new Stack<BFSNode>();
        	BFSNode t = targets.get(i);
        	s.push(t);
        	System.out.println(distance.get(t));
        	if(path.get(t)==null) {
        		if(i<targets.size()-1)
        			writer.write("FAIL\n");
        		else writer.write("FAIL");
        		continue;
        	}
            while(!t.equals(path.get(t))) {
            	s.push(path.get(t));
            	t = path.get(t);
            }
            while(!s.isEmpty()) {
        		BFSNode tmp = s.pop();
        	writer.write(tmp.getY()+","+tmp.getX() + " ");
        	}
            if(i<targets.size()-1)
        	writer.write("\n");
        	
        }    
		writer.close();
		
		
	}

	static void Astar(long W, long H,ArrayList<Long> src, List<BFSNode> targets, long Z, List<List<Long>> hmap) throws IOException
	{
		File outputfile = new File("output.txt");
		FileWriter writer = new FileWriter(outputfile);
		for(long k=0;k<targets.size();k++) {	
		
			PriorityQueue<UCSNode> pQueue= new PriorityQueue<UCSNode>();
			Map<BFSNode, BFSNode> path= new HashMap<>();
			Map<BFSNode, Long> distance = new HashMap<>();
			long costweight=0;
			UCSNode ucs= new UCSNode(0,src.get(1),src.get(0));
			BFSNode nsrc = new BFSNode(src.get(1),src.get(0));
			pQueue.add(ucs);
			distance.put(nsrc,(long) 0);
			path.put(nsrc,nsrc);
			while(!pQueue.isEmpty())
			{	UCSNode topobj= pQueue.poll();
				BFSNode currNode = new BFSNode(topobj.getX(),topobj.getY());
				if(currNode.equals(targets.get((int) k)))	            	
	            		break;
	            
				for(int i=0;i<8;i++)
		            {
		            	long row = currNode.getX() + rowNum[i];
		            	long col = currNode.getY()+colNum[i];
		            	
		            	
		            	BFSNode adjNode = new BFSNode(row,col);
		            	
		            	costweight = findw(currNode,adjNode);
		            	long distadj = 0;
		            	long distcurr = 0;
		            	if(distance.get(adjNode)!=null) {
		            		distadj = distance.get(adjNode);
		            		distcurr = distance.get(currNode);
		            	}
		            	if ((isvalid(W,H,row,col) && distance.get(adjNode)==null && Math.abs(hmap.get((int)row).get((int)col)-hmap.get((int)currNode.getX()).get((int)currNode.getY()))<=Z)){
		            		long zd=Math.abs(hmap.get((int)row).get((int)col)-hmap.get((int)currNode.getX()).get((int)currNode.getY()));
		            		distance.put(adjNode, distance.get(currNode)+costweight+zd);
		            		path.put(adjNode, currNode);
		            		UCSNode ucs1= new UCSNode(distance.get(adjNode)+hfunction(targets.get((int) k),adjNode),row,col);
		            		pQueue.add(ucs1);
		            			            		
		            	}
		            	else if ((isvalid(W,H,row,col) && distadj>distcurr+costweight+Math.abs(hmap.get((int)row).get((int)col)-hmap.get((int)currNode.getX()).get((int)currNode.getY())) && Math.abs(hmap.get((int)row).get((int)col)-hmap.get((int)currNode.getX()).get((int)currNode.getY()))<=Z)){
		            		long zd=Math.abs(hmap.get((int)row).get((int)col)-hmap.get((int)currNode.getX()).get((int)currNode.getY()));
		            		distance.put(adjNode, distance.get(currNode)+costweight+zd);
		            		path.put(adjNode, currNode);
		            		UCSNode ucs1= new UCSNode(distance.get(adjNode)+hfunction(targets.get((int) k),adjNode),row,col);
		            		pQueue.add(ucs1);		            	            		
		
		            	}
		            }
			}
			
	        	Stack<BFSNode> s = new Stack<BFSNode>();
	        	BFSNode t = targets.get((int) k);
	        	s.push(t);
	    		System.out.println("Cost: "+distance.get(t));
	        	if(path.get(t)==null) {
	        		if(k<targets.size()-1)
	        			writer.write("FAIL\n");
	        		else writer.write("FAIL");
	        		continue;
	        	}
	            while(!t.equals(path.get(t))) {
	            	s.push(path.get(t));
	            	t = path.get(t);
	            }
	           while(!s.isEmpty()) {
	        		BFSNode tmp = s.pop();
	        	writer.write(tmp.getY()+","+tmp.getX() + " ");
	        	}
	           if(k<targets.size()-1)
	        	   writer.write("\n");
	}
		writer.close();
		

	}
	private static long hfunction(BFSNode target, BFSNode adjNode) {
		// TODO Auto-generated method stub
		
		return (Math.max(Math.abs(target.getX()-adjNode.getX()),Math.abs(target.getY()-adjNode.getY())));
	}
	public static void main(String args[]) throws Exception
	{
		long st = System.currentTimeMillis();
		File file = new File("input.txt");
		Scanner sc=new Scanner(file);
		
		String algo = sc.nextLine();
		String[] iline = sc.nextLine().split(" ");
		long w = Long.parseLong(iline[0]);
		long h = Long.parseLong(iline[1]);
		iline=sc.nextLine().split(" ");
		ArrayList<Long> src = new ArrayList<Long>();
		src.add(Long.parseLong(iline[0]));
		src.add(Long.parseLong(iline[1]));
		iline=sc.nextLine().split(" ");
		long Z = Long.parseLong(iline[0]);
		iline=sc.nextLine().split(" ");
		long T = Long.parseLong(iline[0]);
		List<BFSNode> targets = new ArrayList<BFSNode>();
		for(long i=0;i<T;i++) {
			iline=sc.nextLine().split(" ");
			BFSNode node = new BFSNode(Long.parseLong(iline[1]), Long.parseLong(iline[0]));
			targets.add(node);
		}
		List<List<Long>> hmap = new ArrayList<List<Long>>();
		for(long i=0;i<h;i++)
			{
				iline=sc.nextLine().split("\\s+");
				List<Long> al = new ArrayList<Long>();
				for(String s:iline)
					al.add(Long.parseLong(s));
				
				hmap.add(al);
			}
		  if(algo.contentEquals("BFS")) {
			homework.BFS(w,h,src,targets,Z,hmap);
	    }
	    else if(algo.contentEquals("UCS"))
				homework.UCS(w,h,src,targets,Z,hmap);
	    else
			homework.Astar(w,h,src,targets,Z,hmap);
		  long endTime = System.currentTimeMillis();
		  System.out.println("Finished time:"+(endTime - st));
	    
	    sc.close();
	   
		
	}

}
