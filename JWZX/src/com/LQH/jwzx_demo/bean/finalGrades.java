package com.LQH.jwzx_demo.bean;

public class finalGrades {

	private String test_time;
	private String name;
	private String curriculum;
	private String ctrriculum_num;
	private String arithmetic;
	private String usually_resuit;
	private String pratice_resuit;
	private String theory_result;
	private String total_score;

	public finalGrades(String name, String test_time, String curriculum,
			String ctrriculum_num, String arithmetic, String usually_resuit,
			String pratice_resuit, String theory_result, String total_score) {
		this.setName(name);
		this.setTest_time(test_time);
		this.setCurriculum(curriculum);
		this.setCtrriculum_num(ctrriculum_num);
		this.setArithmetic(arithmetic);
		this.setUsually_resuit(usually_resuit);
		this.setPratice_resuit(pratice_resuit);
		this.setTheory_result(theory_result);
		this.setTotal_score(total_score);

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

	public String getArithmetic() {
		return arithmetic;
	}

	public void setArithmetic(String arithmetic) {
		this.arithmetic = arithmetic;
	}

	public String getUsually_resuit() {
		return usually_resuit;
	}

	public void setUsually_resuit(String usually_resuit) {
		this.usually_resuit = usually_resuit;
	}

	public String getPratice_resuit() {
		return pratice_resuit;
	}

	public void setPratice_resuit(String pratice_resuit) {
		this.pratice_resuit = pratice_resuit;
	}

	public String getTheory_result() {
		return theory_result;
	}

	public void setTheory_result(String theory_result) {
		this.theory_result = theory_result;
	}

	public String getTotal_score() {
		return total_score;
	}

	public void setTotal_score(String total_score) {
		this.total_score = total_score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
