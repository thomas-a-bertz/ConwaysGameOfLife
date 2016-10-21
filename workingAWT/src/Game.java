import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class Game extends JApplet {

    private static final long serialVersionUID = 1L;
    public static final double VISUAL_WIDTH = 800;
    public static final double VISUAL_HEIGHT = 800;
    private final double cellSizeX;
    private final double cellSizeY;
    private Board board;

    public Game(Board board) {
	this.board = board;
	cellSizeX = VISUAL_WIDTH / board.getDimX();
	cellSizeY = VISUAL_HEIGHT / board.getDimY();
    }

    public void init() {
	Graphics2D g = (Graphics2D) this.getGraphics();
	g.setPaint(Color.white);
	g.fill(new Rectangle2D.Double(0, 0, VISUAL_WIDTH - 1, VISUAL_HEIGHT - 1));
    }

    /** paint the board */
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;

	for (int cellPosY = 0; cellPosY < board.getDimY(); cellPosY++) {
	    for (int cellPosX = 0; cellPosX < board.getDimX(); cellPosX++) {
		if (true == board.cellAlive(cellPosX, cellPosY)) {
		    g2.setPaint(Color.black);
		} else {
		    g2.setPaint(Color.white);
		}
		g2.fill(new Rectangle2D.Double(cellPosX
			/ (double) board.getDimX() * VISUAL_WIDTH, cellPosY
			/ (double) board.getDimY() * VISUAL_HEIGHT, cellSizeX,
			cellSizeY));
	    }
	}
    }

    /** Main function holding the infinite game loop */
    public static void main(String[] args) throws Exception {
	JFrame f = new JFrame("Conway's Game of Life");
	f.setResizable(false);
	f.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	Game applet = new Game(new Board(50, 50, null));
	f.getContentPane().add("Center", applet);
	f.pack();
	f.setSize((int) Game.VISUAL_WIDTH, (int) Game.VISUAL_HEIGHT);
	applet.init();
	f.setVisible(true);
	while (true) {
	    System.out.println(applet.getBoard());
	    applet.repaint();
	    Thread.currentThread().sleep(300);
	    applet.tick();
	}
    }

    private void tick() {
	board.calculateNext();
    }

    private Board getBoard() {
	return board;
    }
}
