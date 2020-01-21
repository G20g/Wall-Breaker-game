public class Mapping {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public Mapping(int row,int col) {
        map = new int[row][col];
        for(int i=0;i<map.length;i++) {
            for(int j=0;j<map[0].length;j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;

    }
}
