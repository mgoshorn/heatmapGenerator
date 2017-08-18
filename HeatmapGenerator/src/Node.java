import java.util.ArrayList;

public class Node {
	private float quality;
	int x, y;
	
	/**
	 * Create a new Node
	 * @param x x-position
	 * @param y y-position
	 */
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Assign a quality to the Node
	 * @param quality
	 */
	public void setQuality(float quality) {
		this.quality = quality;
		if(this.quality > 1f) this.quality = 1f;
		if(this.quality < 0f) this.quality = 0f;
	}
	
	/**
	 * Get quality of the node
	 * @return quality of node
	 */
	public float getQuality() {
		return this.quality;
	}
	
	/**
	 * Find all valid neighbors of the given Node
	 * @param map matrix to be used
	 * @return array of nodes representing neighboring nodes
	 */
	public ArrayList<Node> findNeighbors(Node[][] map) {
		ArrayList<Node> neighbors = new ArrayList<>();
		for(int x = this.x - 1; x < this.x + 2; x++) {
			for(int y = this.y - 1; y < this.y + 2; y++) {			
			
				//Get x and y values that rollover the matrix
				int dx = Math.floorMod(x, map.length);
				int dy = Math.floorMod(y, map[dx].length);
				
				//Retrieve the node at this location
				Node neighbor = map[dx][dy];
			
				/* Don't add any nodes if:
				*	-It is the current node
				*	-The index location is a null reference
				*/
				if(neighbor == null || neighbor.equals(this)) continue;
				neighbors.add(neighbor);
			}
		}
		return neighbors;
	}
}
