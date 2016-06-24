package xupt.se.ttms.service;

import xupt.se.ttms.dao.SeatDAO;
import xupt.se.ttms.idao.DAOFactory;
import xupt.se.ttms.idao.iSeatDAO;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.util.DBUtil;

import java.util.LinkedList;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

/**
 * Created by yang on 16-6-17.
 */
public class SeatSrv {
    private iSeatDAO seatDAO = DAOFactory.createSeatDAO();

    public int add(Seat seat) {
        return seatDAO.insert(seat);

    }

    public int modify(Seat seat) {
        return seatDAO.update(seat);
    }

    public int delete(int id) {
        return seatDAO.delete(id);
    }

    public List<Seat> Fetch(String condt) {
        return seatDAO.select(condt);
    }

    public int init(int studio_id) throws Exception {
        int row = 0, col = 0;
        StudioSrv stusrv = new StudioSrv();
        Studio simple = stusrv.Fetch("studio_id=" + studio_id).get(0);
        row = simple.getRowCount();
        col = simple.getColCount();
        simple.setFlag(1);
        stusrv.modify(simple);
        StringBuffer sql = new StringBuffer("insert seat values");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
               /* Seat seat=new Seat();
                seat.setStuId(studio_id);
                seat.setsRow(i+1);
                seat.setScolumn(j+1);
                seat.setSeat_status(1);
                new SeatDAO().insert(seat);*/
                sql.append("(NULL," + studio_id + "," + (i + 1) + "," + (j + 1) + ",1)");
                if (i * j != (row - 1) * (col - 1)) {
                    sql.append(",");
                }

            }
        }
        System.out.println(sql);

        DBUtil db = new DBUtil();
        db.openConnection();
        db.execCommand(sql.toString());
        return 1;
    }
/*
   获取座位状态数组，显示座位用
 */
    public int[][] getseatarray(int studio_id, int row, int col) {
        List<Seat> seat = new SeatSrv().Fetch("studio_id=" + studio_id);
        int j = 0, i = 0, l = seat.size();
        int[][] a = new int[row][col];
        for (Object s : seat) {
            a[j][i] = ((Seat) s).getSeat_status();
            if (++i == col) {
                j++;
                i = 0;
            }
        }

        return a;
    }

    /**
     *
     * @param studio_id 修改座位
     * @param row
     * @param col
     * @param flag
     * @return
     */



    public int modifyseat(int studio_id, int row, int col, int flag) {
        List<Seat> seatlist = this.Fetch("studio_id=" + studio_id);
        for (Object s : seatlist) {
            Seat seat = (Seat) s;
            if (seat.getStuId() == studio_id && seat.getsRow() == row && seat.getScolumn() == col) {
                seat.setSeat_status(flag);
                this.modify(seat);
                return 1;
            }
        }
        return 0;
    }
}
