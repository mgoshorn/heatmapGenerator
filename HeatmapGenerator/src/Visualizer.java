import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Visualizer extends JPanel {
	
	private static final Dimension windowDimension = new Dimension(1600, 1600);
	private static final long serialVersionUID = -2549534001025211766L;
	private Generator generator;
	
	public Visualizer(Generator generator) {
		//Display setup
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.BLACK);
		this.setSize(windowDimension);
		frame.setSize(windowDimension);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBackground(Color.BLACK);
		frame.add(this);
		frame.setVisible(true);
		this.generator = generator;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		//Gets window sizing
		int height = windowDimension.width;
		int width  = windowDimension.height;
		
		//Calculates appropriate cell size for the window dimensions
		double cellWidth = (double)height / this.generator.map.length;
		double cellHeight = (double)width / this.generator.map[0].length;
		
		int size = this.generator.map.length * this.generator.map[0].length;
		
		//Iterates through matrix and draws each cell
		for(int x = 0; x < this.generator.map.length; x++) {
			for(int y = 0; y < this.generator.map[x].length; y++) {
				//Get color for this quality
				System.out.printf("Processing visualization: %4.2f%%\r", ((float)x*this.generator.map[x].length*100 + y) / size);
				
				Color c = findColor(this.generator.map[x][y].getQuality());
				
				//Set color to graphics object
				g2.setColor(c);
				
				//Create rectangle at the appropriate location
				Rectangle2D rect = new Rectangle2D.Double(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
				
				//Draw rectangle
				g2.fill(rect);
			}
		}
		System.out.println("Processing visualization: 100.00%");
		System.out.print("Done!");
	}
	
	//Color set used for visualization
	private static Color colors[] = {new Color(0, 0, 0, 255), new Color(133, 60, 8, 255), Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW,
			Color.RED, Color.MAGENTA};
	
	/**
	 * Interpolates two colors using a given index value between 0 and 1
	 * @param a First color
	 * @param b Second color
	 * @param index index value between 0 and 1
	 * @return interpolated color
	 */
	private static Color interpolate(Color a, Color b, float index) {
		return new Color(
				(int)(a.getRed() 	* (1f-index) + b.getRed() 	* index),
				(int)(a.getGreen() 	* (1f-index) + b.getGreen() * index),
				(int)(a.getBlue() 	* (1f-index) + b.getBlue() 	* index),
				(int)(a.getAlpha() 	* (1f-index) + b.getAlpha() * index));
	}
	
	/**
	 * Returns a color for the given quality index. Method will choose the two appropriate colors,
	 * then interpolate them based on the index, returning the interpolated color
	 * @param index index value used for determining colors, should be the quality of the node
	 * @return interpolated color between two colors chosen from the color set
	 */
	public static Color findColor(float index) {
		Color[] interpolationPair = new Color[2];
		//Determine the index that will be passed for interpolating between the two colors
		float interpolationIndex = (index * colors.length) % 1f;
		
		//Choose the two colors to interpolate
		if(index > ((double)colors.length - 1) / colors.length) {
			//In case the top two colors need to be chosen (maximal value correction)
			interpolationPair[0] = colors[colors.length - 1]; 
			interpolationPair[1] = colors[colors.length - 2]; 
		} else {
			//Finds the correct location based on the index value, then adds the two appropriate
			//colors to the set
			int location = (int)(index * (float)colors.length);
			interpolationPair[0] = colors[location];
			interpolationPair[1] = colors[location+1];
		}
		
		//Interpolate selected colors and return the interpolated color
		return interpolate(interpolationPair[0], interpolationPair[1], interpolationIndex);
	}
	
}
