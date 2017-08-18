import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class Generator {

	Node[][] map;
	Visualizer visualization;
	
	private static final float minimalChange = -0.5f;
	private static final float maximalChange = 0.015f;
	private static final float deltaMultiplier = maximalChange - minimalChange;
	
	
	//Matrix dimensions
	private static final Dimension graphSize = new Dimension(1000, 1000);
	
	/*Number of selections relative to the total nodes in the matrix
	* Each selection will serve as a entrance into the recursive function which
	* generates individual bursts in the heath map
	*/
	private static final double selections = 0.003;
	private static final int selectionsTotal = (int)(graphSize.getHeight() * graphSize.getWidth() * selections);
	
	
	//Instantiate generator
	public Generator(Dimension dimension) {
		//setup graph 
		this.map = new Node[(int)dimension.getWidth()][(int)dimension.getWidth()];
		for(int x = 0; x < this.map.length; x++) {
			for(int y = 0; y < this.map[x].length; y++) {
				this.map[x][y] = new Node(x, y);
			}
		}
	}

	public static void main(String[] args) {
		Random rand = new Random(12);
		Generator generation = new Generator(graphSize);
		
		for(int i = 0; i < selectionsTotal; i++) {
			System.out.println("Selection " + i + "/" + selectionsTotal + "(" + ((float)i / selectionsTotal) + "%)");
			
			
			//get starting position
			int x = rand.nextInt(generation.map.length);
			int y = rand.nextInt(generation.map[0].length);
			
			//Generate a starting quality
			float quality = rand.nextFloat();
			if(generation.map[x][y].getQuality() > quality) continue;
			//Assign quality to node
			generation.map[x][y].setQuality(quality);
			
			//add starting point to list
			//Stack would be a better choice, but linked list is running a lot faster
			//probably due to the contains option
			Stack<Node> open = new Stack<>();
			
			//Closed set to match against
			Set<Node> closedSet = new HashSet<>();
			
			open.add(generation.map[x][y]);
			
			while(!open.isEmpty()) {
				//Node n = open.pop();
				Node n = open.get(0);
				open.remove(0);
				closedSet.add(n);
				
				if(n.getQuality() <= 0.05) continue;
				//System.out.println(n.getQuality);
				ArrayList<Node> neighbors = n.findNeighbors(generation.map);
				for(Node neighbor : neighbors) {
					if(neighbor.getQuality() >= n.getQuality()) continue;
					
					float delta = (float)rand.nextDouble() * deltaMultiplier + minimalChange;
					
					//neighbor.getQuality = n.getQuality + delta;
					if(neighbor.getQuality() >= n.getQuality() + delta) continue;
					neighbor.setQuality(n.getQuality() + delta);
					
					//Add 
					if(neighbor.getQuality() > 0 && !closedSet.contains(neighbor)) open.add(neighbor);
					
				}
			}

		}
		
		//affected Nodes
		int affected = 0;
		for(int x = 0; x < generation.map.length; x++) {
			for(int y = 0; y < generation.map[x].length; y++) {
				if(generation.map[x][y].getQuality() > 0.05) affected++;
			}
		}
		System.out.println("Affected: " + affected);
		
		generation.visualization = new Visualizer(generation);
		generation.visualization.repaint();
	}
	
}
