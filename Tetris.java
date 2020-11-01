import java.awt.*;

public class Tetris {
    int width = 20; // set board width
    int height = 40; // set board height
    Color[][] board = new Color[width][height];
    Tetromino currentpiece = new Tetromino();
    Tetris() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == 0 || x == 0 || x == 19 || y == 39) {
                    board[x][y] = Color.PINK; // fill borders of board
                }
                else {
                    board[x][y] = Color.lightGray; // fill board
                }
            }
        }
    }
    public void drawPiece(Graphics g) {
        for (int i = 0; i < currentpiece.piece[currentpiece.rotation].length; i++) {
            g.setColor(currentpiece.color);
            g.fillRect((currentpiece.x + currentpiece.piece[currentpiece.rotation][i].x) * 20, (currentpiece.y +
                    currentpiece.piece[currentpiece.rotation][i].y)*20, 20,20);
            g.setColor(Color.darkGray);
            g.drawRect((currentpiece.x + currentpiece.piece[currentpiece.rotation][i].x)*20,
                    (currentpiece.y + currentpiece.piece[currentpiece.rotation][i].y)*20,20,20);
        }
    }

    public void clearRow() {
        boolean rowFilled = false;
        for(int y = height - 2; y != 1; y--) { // check between borders
            for (int x = 1; x < width-1; x++) { // check between borders
                rowFilled = !board[x][y].equals(Color.lightGray); // board is filled so no gray background
                if (!rowFilled) { // if not filled don't clear
                    break;
                }
            }
            if(rowFilled) { // if filled, shift all rows down and remove row
                for(int i = y; i != 2; i--) {
                    for (int j = 1; j < width - 1; j++) {
                        board[j][i] = board[j][i-1];
                    }
                }
                y++;
                
            }
            rowFilled = false; // reset as false as row has been cleared or just not filled to begin with
        }
    }

    public void placePiece(){ // place a piece at a specific rotation and maintain rotation
        for(int i = 0; i < currentpiece.piece[currentpiece.rotation].length; i++) {
            board[currentpiece.piece[currentpiece.rotation][i].x + currentpiece.x]
                    [currentpiece.piece[currentpiece.rotation][i].y + currentpiece.y] = currentpiece.color;
        }
        currentpiece = new Tetromino();
        clearRow(); // check to see if clearing of rows is necessary

    }

    public boolean isOver() {
        boolean gameOver = false;
        for(int x = 1; x < width - 1; x++) { // excludes border
            gameOver = !board[x][1].equals(Color.lightGray); // if a line at top of board is filled with colors
        }
        return gameOver;
    }
    public void draw(Graphics g) {
        if (isOver()) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,width*20,height*20);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("GAME OVER", width*10-90,height*10-15);
        }
        else {
            for (int x = 0; x < width; x++) {
                for(int y = 0; y < height; y++) {
                    g.setColor(board[x][y]);
                    g.fillRect(x*20, y*20,20,20);
                    g.setColor(Color.darkGray);
                    g.drawRect(x*20,y*20,20,20);
                }
            }
            drawPiece(g);
        }
    }

    public void rotateLeft() {
        currentpiece.rotation = currentpiece.rotation == 0 ? 3 : currentpiece.rotation - 1;
        // if rotation of current piece equals zero then change to 3
        // else subtract one from the piece rotation to change rotation
        if (currentpiece.checkCollision(0,0,board)) {
            currentpiece.rotation = currentpiece.rotation == 3 ? 0 : currentpiece.rotation + 1;
        }
    }

    public void moveLeft() {
        if (!currentpiece.checkCollision(-1,0,board)) {
            // check to see if piece hasn't already hit left side of board
            currentpiece.x--; // subtract one from x coordinate to shift block left
        }
    }

    public void moveRight() {
        if (!currentpiece.checkCollision(1,0,board)) {
            // check to see if piece isn't already at the far left of the board then it has space to move right
            currentpiece.x++; // add one to x coordinate to shift block right
        }
    }

    public void tick() {
        if (!currentpiece.checkCollision(0,1,board)) {
            // check that the piece isn't already at the top of board with another piece
            currentpiece.y++; // move down board by one increase of y-axis
        }
        else {
            placePiece();
        }
    }


}
