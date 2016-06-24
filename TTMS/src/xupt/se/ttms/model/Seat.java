package xupt.se.ttms.model;

/**
 * Created by yang on 16-6-14.
 */
public class Seat {
    private int seatId; //座位id
    private int stuId; //演出厅id
    private int sRow; //行数
    private int scolumn;//列数
    private int seat_status;//座位状态0:此处是空位,没有安排桌椅;1:完好的座位,可以使用.-1:座位损坏，不能安排座位.
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public int getsRow() {
        return sRow;
    }

    public void setsRow(int sRow) {
        this.sRow = sRow;
    }

    public int getScolumn() {
        return scolumn;
    }

    public void setScolumn(int scolumn) {
        this.scolumn = scolumn;
    }

    public int getSeat_status() {
        return seat_status;
    }

    public void setSeat_status(int seat_status) {
        this.seat_status = seat_status;
    }
}
