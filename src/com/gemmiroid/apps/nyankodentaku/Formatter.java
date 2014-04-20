package com.gemmiroid.apps.nyankodentaku;


import java.text.DecimalFormat;

public class Formatter {
	String MainPanel;
	String SubPanel;

	// このFormatterが使われるとき、こと計算結果においては
	// あらかじめ小数点以下を丸めてから呼び出される。
	// その処理については、Calcuratorクラスに記載する
	String setPanel(String strNum) {
		String strDecimal = "";
		String strInt = "";
		String whole_num = "";
		String point_after = "";

		DecimalFormat form = new DecimalFormat("##,###,###");

		String formatter = strNum;
		String[] formatterArray = formatter.split("\\.");

		whole_num = formatterArray[0];

		if (whole_num.length() > 12) {
			// 入力時のエラー回避に必要
			return "error";
		} else {
			whole_num = form.format(Double.parseDouble(whole_num));
			if (formatterArray.length >1) {
				point_after = formatterArray[1];
				point_after = form.format(Double.parseDouble(point_after));
				whole_num += ("." + point_after);
			}
			return whole_num;
		}
	}
}
