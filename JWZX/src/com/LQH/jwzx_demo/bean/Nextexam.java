package com.LQH.jwzx_demo.bean;

public class Nextexam {

	private String test_time, name, curriculum, ctrriculum_num, XQ, XY, BJ,
			KCLX, JSH, GLDW, KSFS;

	public Nextexam(String name, String test_time, String curriculum,
			String ctrriculum_num, String XQ, String XY, String BJ,
			String KCLS, String JSH, String GLDW, String KSFS) {
		this.setName(name);
		this.setTest_time(test_time);
		this.setCurriculum(curriculum);
		this.setCtrriculum_num(ctrriculum_num);
		this.setXQ(XQ);
		this.setXY(XY);
		this.setBJ(BJ);
		this.setKCLX(KCLS);
		this.setJSH(JSH);
		this.setGLDW(GLDW);
		this.setKSFS(KSFS);

	}

	public String getTest_time() {
		return test_time;
	}

	public void setTest_time(String test_time) {
		this.test_time = test_time;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public String getCtrriculum_num() {
		return ctrriculum_num;
	}

	public void setCtrriculum_num(String ctrriculum_num) {
		this.ctrriculum_num = ctrriculum_num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXQ() {
		return XQ;
	}

	public void setXQ(String xQ) {
		XQ = xQ;
	}

	public String getXY() {
		return XY;
	}

	public void setXY(String xY) {
		XY = xY;
	}

	public String getBJ() {
		return BJ;
	}

	public void setBJ(String bJ) {
		BJ = bJ;
	}

	public String getKCLX() {
		return KCLX;
	}

	public void setKCLX(String kCLX) {
		KCLX = kCLX;
	}

	public String getJSH() {
		return JSH;
	}

	public void setJSH(String jSH) {
		JSH = jSH;
	}

	public String getGLDW() {
		return GLDW;
	}

	public void setGLDW(String gLDW) {
		GLDW = gLDW;
	}

	public String getKSFS() {
		return KSFS;
	}

	public void setKSFS(String kSFS) {
		KSFS = kSFS;
	}

}
