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
				/*Don't add any nodes if:
				*	-It goes beyond the graph size
				*	-It is the current node
				*	-The index location is a null reference
				*/
				if(x < 0 || x >= map.length || y < 0 || y >= map[x].length || this.equals(map[x][y]) || map[x][y] == null) continue;
				neighbors.add(map[x][y]);
			}
		}
		return neighbors;
	}
}
