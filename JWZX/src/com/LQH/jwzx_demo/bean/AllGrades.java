package com.LQH.jwzx_demo.bean;

public class AllGrades {

	private String KCH;
	private String KCM;
	private String SDXF;
	private String KCCJ;
	private String BKCJ;
	private String BZF;

	public AllGrades(String KCH, String KCM, String SDXF, String KCCJ,
			String BKCJ, String BZF) {
		this.setKCH(KCH);
		this.setKCM(KCM);
		this.setSDXF(SDXF);
		this.setKCCJ(KCCJ);
		this.setBKCJ(BKCJ);
		this.setBZF(BZF);

	}

	public String getKCH() {
		return KCH;
	}

	public void setKCH(String kCH) {
		KCH = kCH;
	}

	public String getKCM() {
		return KCM;
	}

	public void setKCM(String kCM) {
		KCM = kCM;
	}

	public String getSDXF() {
		return SDXF;
	}

	public void setSDXF(String sDXF) {
		SDXF = sDXF;
	}

	public String getKCCJ() {
		return KCCJ;
	}

	public void setKCCJ(String kCCJ) {
		KCCJ = kCCJ;
	}

	public String getBKCJ() {
		return BKCJ;
	}

	public void setBKCJ(String bKCJ) {
		BKCJ = bKCJ;
	}

	public String getBZF() {
		return BZF;
	}

	public void setBZF(String bZF) {
		BZF = bZF;
	}

}
