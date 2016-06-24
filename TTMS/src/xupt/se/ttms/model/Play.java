package xupt.se.ttms.model;

/**
 * Created by yang on 16-6-14.
 */
public class Play {
    private int pId; //剧目id
    private int pTypeId; //类型id
    private int pLangId; //语种id
    private String pName; //剧目名称
    private String pIntro; //剧目介绍
    private String pImage; //剧目介绍图片
    private int pLength; //剧目时长
    private float pTicketprice; //票价
    private int pStatus; //票的状态0:待安排,1:已安排,-1:下线

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getpTypeId() {
        return pTypeId;
    }

    public void setpTypeId(int pTypeId) {
        this.pTypeId = pTypeId;
    }

    public int getpLangId() {
        return pLangId;
    }

    public void setpLangId(int pLangId) {
        this.pLangId = pLangId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpIntro() {
        return pIntro;
    }

    public void setpIntro(String pIntro) {
        this.pIntro = pIntro;
    }

    public String getpImage() {
        if (pImage == null){
            return pImage;
        }
        if (pImage.equals("null")){
            pImage = null;
        }
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public int getpLength() {
        return pLength;
    }

    public void setpLength(int pLength) {
        this.pLength = pLength;
    }

    public float getpTicketprice() {
        return pTicketprice;
    }

    public void setpTicketprice(float pTicketprice) {
        this.pTicketprice = pTicketprice;
    }

    public int getpStatus() {
        return pStatus;
    }

    public void setpStatus(int pStatus) {
        this.pStatus = pStatus;
    }
}
