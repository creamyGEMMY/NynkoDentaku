package com.gemmiroid.apps.nyankodentaku;

import java.math.BigDecimal;

import android.view.View;

public class Calcurator {
	String tv_main = "0";
	String tv_sub = "";
	String tv_copy = "今はカラだよ";
	String inputtingA = "";
	String inputtingB = "";
	boolean inputFlag = true;
	boolean copyFlag = false;
	boolean overFlag = false;
	boolean errorFLAG = false;
	String opeKey, exOpeKey;

	String doCopy(String s) {
		if (!copyFlag) {
			copyFlag = true;
			s = tv_main;
			tv_copy = s;
			return "changeBtn";
		} else {
			copyFlag = false;
			if (inputFlag) {
				inputtingA = s;
			} else {
				inputtingB = s;
			}
			tv_copy = "今はカラだよ";
			tv_main = s;
		}
		return "ERROR";
	}

	String doBackSpace() {
		String inputting;
		if (inputFlag)
			inputting = inputtingA;
		else
			inputting = inputtingB;

		if (inputting.length() == 0)
			return inputting;
		else
			inputting = inputting.substring(0, inputting.length() - 1);

		if (inputFlag)
			inputtingA = inputting;
		else
			inputtingB = inputting;

		return inputting;

	}

	void doClear() {
		if (inputFlag) {
			inputtingA = "";
		} else {
			inputtingB = "";
		}
		tv_main = "";
	}

	void allClear() {
		this.tv_main = "";
		this.tv_sub = "";
		this.inputtingA = null;
		this.inputtingB = null;
		this.inputFlag = true;
		this.opeKey = null;
		this.exOpeKey = null;
	}

	String doPercent() {
			Double d1 = Double.parseDouble(inputtingA);
			Double d2 = Double.parseDouble(inputtingB);
			Double result = null;

			if (opeKey == "+") {
				result = (d1 + d1 / 100 * d2) - d1;;
			} else if (opeKey == "-") {
				result = (d1 - d1 / 100 * d2) - d1;
			} else if (opeKey == "*") {
				result = (d1 / 100) * d2;
			} else if (opeKey == "/") {
				if (inputtingB != "0") {
					result = (d1 / (d2 / 100));
				} else {
					return "ERROR";
				}
			}
			BigDecimal big = new BigDecimal(result);
			BigDecimal big2 = big.setScale(5, BigDecimal.ROUND_HALF_UP);
			String s = (big2.toString());

			return s;
	}
	
	public String doCalc() {
		BigDecimal bd1 = new BigDecimal(inputtingA);
		BigDecimal bd2 = new BigDecimal(inputtingB);
		BigDecimal result = BigDecimal.ZERO;

		if (opeKey == "+") {
			result = bd1.add(bd2);
		} else if (opeKey == "-") {
			result = bd1.subtract(bd2);
		} else if (opeKey == "*") {
			result = bd1.multiply(bd2);
		} else if (opeKey == "/") {
			if (!bd2.equals(BigDecimal.ZERO)) {
				result = bd1.divide(bd2, 12, 3);
			} else {
				errorFLAG = true;
			}
		}
		tv_main = result.toString();
		return tv_main;
	}

	// なんか動きがおかしい。要チェックやで。
	public String doChange() {
		// TODO 自動生成されたメソッド・スタブ
		String inputting;
		inputting = tv_main;

		if (inputting == null) {
			return "";
		} else if (inputting.startsWith("-")) {
			inputting.replace("-", "");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(inputting);
			sb.insert(0, "-");
			inputting = sb.toString();
		}

		if (inputFlag) {
			inputtingA = inputting;
		} else {
			inputtingB = inputting;
		}

		return inputting;
	}

	public String doRepeat() {
		String result;
		// TODO 自動生成されたメソッド・スタブ
		if (opeKey != null && inputtingA != null && inputtingB != null) {
			if (opeKey == "EQ") {
				opeKey = exOpeKey;
				result = doCalc();
				tv_sub = inputtingA + opeKey + inputtingB + "=";
				opeKey = "EQ";
				inputtingA = result;
				return result;
			} else if (opeKey != "EQ") {
				result = doCalc();
				tv_sub = inputtingA + opeKey + inputtingB + "=";
				opeKey = "EQ";
				inputtingA = result;
				return result;
			} else {
				return "ERROR";
			}
		}
		return "ERROR";
	}

	public void doOperatorSign(String mOpeSign) {
		// TODO 自動生成されたメソッド・スタブ
		if (opeKey != null) {

		}
	}

	public void doInputNum(String pressed_key) {
		if (opeKey != null) {
			if (opeKey.equals("EQ")) {
				tv_sub = "";
				inputtingA = null;
				inputtingB = null;
				inputFlag = true;
				opeKey = null;
			}
		}
		String inputting;
		if (inputFlag) {
			inputting = inputtingA;
		} else {
			inputting = inputtingB;
		}

		if (inputting == null) {
			inputting = "";
		}

		if (pressed_key.equals(".")) {
			if (inputting.length() == 0) {
				inputting = "0.";
			} else {
				if (inputting.indexOf(".") == -1)
					inputting = inputting + ".";
			}
		} else {
			inputting = inputting + pressed_key;
		}
		tv_main = inputting;

		if (inputFlag) {
			inputtingA = inputting;
		} else {
			inputtingB = inputting;
		}
	}

	public void doInputOpe(View v) {
		// TODO 自動生成されたメソッド・スタブ
		String s = null;
		switch (v.getId()) {
		case R.id.calcDiv:
			s = "/";
			break;
		case R.id.calcMulti:
			s = "*";
			break;
		case R.id.calcAdd:
			s = "+";
			break;
		case R.id.calcSub:
			s = "-";
			break;
		}
		if (opeKey == "EQ") {
			inputFlag = false;
			inputtingB = "";
			exOpeKey = opeKey;
			opeKey = s;
			tv_sub = inputtingA + opeKey;
		} else {
			if (opeKey == null && inputtingA != null) {
				opeKey = s;
				inputFlag = !inputFlag;
				tv_sub = inputtingA + opeKey;
				inputtingB = "";
				tv_main = "";
			} else if (opeKey != null && inputtingB == null) {
				opeKey = s;
				tv_sub = inputtingA + opeKey;
			} else if (opeKey != null && inputtingB != null) {
				String result = doCalc();
				exOpeKey = opeKey;
				opeKey = s;
				inputtingA = result;
				tv_sub = inputtingA + opeKey;
				inputtingB = "";
				tv_main = "";
			}
		}
	}

	public void doEq() {
		// TODO 自動生成されたメソッド・スタブ
		if (inputtingA == null) {
			inputtingA = "0";
		} else if (inputtingA != null && opeKey == null) {
			return;
		} else if (inputtingA != null && inputtingB == null || inputtingB == "") {
			return;
		} else {
			String result = doCalc();
			exOpeKey = opeKey;
			opeKey = "EQ";
			inputtingA = result;
			tv_sub += inputtingB + "=";
		}
	}
}
