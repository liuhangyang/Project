package xupt.se.ttms.model;

/**
 * Created by yang on 16-6-14.
 */
public class Sale_item {
    private  long sItemid;//单张票订单id
    private long tId; //票id
    private long saleId;
    private long saleItemprice; //单笔

    public long getsItemid() {

        return sItemid;
    }

    public void setsItemid(long sItemid) {
        this.sItemid = sItemid;
    }

    public long gettId() {
        return tId;
    }

    public void settId(long tId) {
        this.tId = tId;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public long getSaleItemprice() {
        return saleItemprice;
    }

    public void setSaleItemprice(long saleItemprice) {
        this.saleItemprice = saleItemprice;
    }
}