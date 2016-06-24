package xupt.se.ttms.model;

/**
 * Created by lc on 2016/6/16.
 */
public class SeatList {

    int[][] seatList;
    int col;
    int row;
    String clsName[] = {  "seat_ept", "seat_nor", "seat_brk", "seat_nor seat_sed"};
//    0 空位  1 正常   2 坏坐  3已售出票
    public SeatList(int[][] seatList, int row, int rol) {
        this.seatList = seatList;
        this.col = rol;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getVal(int x, int y){
        if(x < row && y < col){
            return seatList[x][y];
        }
        return -1;
    }

    public String getClsName(int k){
        if (k < 0 || k > clsName.length) return "Error";
        return clsName[k];
    }

}
