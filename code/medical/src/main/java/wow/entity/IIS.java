package wow.entity;

public class IIS {
	
	private Integer i1;
	private Integer i2;
	private String s;
	public IIS(Integer i1, Integer i2, String s) {
		super();
		this.i1 = i1;
		this.i2 = i2;
		this.s = s;
	}
	public IIS() {
		super();
		
	}
	public Integer getI1() {
		return i1;
	}
	public void setI1(Integer i1) {
		this.i1 = i1;
	}
	public Integer getI2() {
		return i2;
	}
	public void setI2(Integer i2) {
		this.i2 = i2;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}

}
