package xupt.se.ttms.idao;

import xupt.se.ttms.dao.*;

public class DAOFactory {
    public static iStudioDAO creatStudioDAO() {
        return new StudioDAO();
    }

    public static iEmployeeDAO creatEmployeeDAO() {
        return new EmployeeDAO();
    }

    public static iPlayDAO createPlayDAO() {
        return new PlayDAO();
    }

    public static iSaleDAO createSaleDAO() {
        return new SaleDAO();
    }

    public static iScheduleDAO createScheduleDAO() {
        return new ScheduleDAO();
    }

    public static iSale_itemDAO createSaleitemDAO() {
        return new Sale_itemDAO();
    }

    public static iSeatDAO createSeatDAO() {
        return new SeatDAO();
    }

    public static IDataDictDAO creatDataDictDAO() { return new DataDictDAO();  }
    public static iTicketDAO createTicketDAO(){
        return  new TicketDAO();
    }
}
