public class Game {

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

    /** Main function holding the infinite game loop */
    public static void main(String[] args) throws Exception {
	Game applet = new Game(new Board(50, 50, 42L));
	int generationCount = 700;
	while (generationCount > 0) {
	    System.out.println(applet.getBoard());
	    Thread.currentThread().sleep(300);
	    applet.tick();
	    generationCount--;
	}
    }

    private void tick() {
	board.calculateNext();
    }

    private Board getBoard() {
	return board;
    }
}

