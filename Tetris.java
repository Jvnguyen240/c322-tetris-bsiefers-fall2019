import java.awt.*;

public class Tetris {
    int width = 20;
    int height = 40;
    Color[][] board = new Color[width][height];
    Tetrimino currentpeice = new Tetrimino();
    Tetris(){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(y == 0 || x == 0 || x == 19 || y == 39){
                    board[x][y] = Color.gray;
                }else{
                    board[x][y] = Color.lightGray;
                }

            }
        }
    }
    public void clearRows(){
        boolean rowCompleted = false;
        for(int y = height-2; y != 1; y--) {
            for (int x = 1; x < width-1; x++) {
                rowCompleted = !board[x][y].equals(Color.lightGray);
                if(!rowCompleted)
                    break;
            }
            if(rowCompleted){
                for(int i = y; i != 2; i--) {
                    for (int j = 1; j < width - 1; j++) {
                        board[j][i] = board[j][i-1];
                    }
                }
                y++;
            }
            rowCompleted = false;
        }
    }

    public void placePiece(){
        for(int i = 0; i < currentpeice.piece[currentpeice.rotation].length; i++) {
            board[currentpeice.piece[currentpeice.rotation][i].x + currentpeice.x]
                 [currentpeice.piece[currentpeice.rotation][i].y + currentpeice.y] = currentpeice.color;
        }
        currentpeice = new Tetrimino();
        clearRows();

    }

    public boolean checkGameOver(){
        boolean gameOver = false;
        for(int x = 1; x < width-1; x++){
            gameOver = !board[x][1].equals(Color.lightGray);
            if(gameOver) break;
        }
        return gameOver;
    }
    public void tick(){
        if(!currentpeice.checkCollision(0, 1, board)){
            currentpeice.y++;
        }else{
            placePiece();
        }
    }

    public void rotateLeft(){

        currentpeice.rotation = currentpeice.rotation == 0 ? 3 : currentpeice.rotation-1;
        if(currentpeice.checkCollision(0,0, board)){
            currentpeice.rotation = currentpeice.rotation == 3 ? 0 : currentpeice.rotation+1;
        }
    }

    public void rotateRight(){

        currentpeice.rotation = currentpeice.rotation == 3 ? 0 : currentpeice.rotation+1;
        if(currentpeice.checkCollision(0,0, board)){
            currentpeice.rotation = currentpeice.rotation == 0 ? 3 : currentpeice.rotation-1;
        }
    }

    public void moveLeft(){
        if(!currentpeice.checkCollision(-1, 0, board)){
           currentpeice.x--;
        }
    }

    public void moveRight(){
        if(!currentpeice.checkCollision(1, 0, board)){
            currentpeice.x++;
        }
    }
    public void draw(Graphics g){

        if(checkGameOver()){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0, width*20, height*20);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("GAME OVER", width*10-90, height*10-15);
        }else{
            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    g.setColor(board[x][y]);
                    g.fillRect(x*20, y*20, 20, 20);
                    g.setColor(Color.darkGray);
                    g.drawRect(x*20, y*20, 20, 20);
                }
            }
            drawPiece(g);
        }
    }

    public void drawPiece(Graphics g){
        for(int i = 0; i < currentpeice.piece[currentpeice.rotation].length; i++){
            g.setColor(currentpeice.color);
            g.fillRect((currentpeice.x + currentpeice.piece[currentpeice.rotation][i].x) *20, (currentpeice.y + currentpeice.piece[currentpeice.rotation][i].y)*20, 20, 20);
            g.setColor(Color.darkGray);
            g.drawRect((currentpeice.x + currentpeice.piece[currentpeice.rotation][i].x) *20, (currentpeice.y + currentpeice.piece[currentpeice.rotation][i].y)*20, 20, 20);
        }
    }

}