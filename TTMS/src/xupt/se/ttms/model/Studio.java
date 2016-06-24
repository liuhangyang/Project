package xupt.se.ttms.model;

public class Studio {
	private int id = 0;
	private String name = "";
	private int rowCount = 0;
	private int colCount = 0;
	private String introduction = "";
	private int flag = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setID(int ID) {
		this.id = ID;
	}

	public int getID() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setRowCount(int count) {
		this.rowCount = count;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setColCount(int count) {
		this.colCount = count;
	}

	public int getColCount() {
		return colCount;
	}

	public void setIntroduction(String intro) {
		this.introduction = intro;
	}

	public String getIntroduction() {
		return introduction;
	}
}
