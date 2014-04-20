package com.gemmiroid.apps.nyankodentaku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public TextView tv_mainPanel, tv_subPanel, tv_copyPanel;
	private LinearLayout menu, menulayout;
	private Animation open, close;
	private Calcurator calc;
	private MediaPlayer cover, numKey, menuKey, howto;
	private ImageButton btn_How_to, btn_MoreApps, btn_StartOtoku;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// manifestでタイトルバー出さない処理しているので、そのままレイアウトをセット
		setContentView(R.layout.activity_main);

		cover = MediaPlayer.create(this, R.raw.catvoice);
		howto = MediaPlayer.create(this, R.raw.cat6);
		numKey = MediaPlayer.create(this, R.raw.kitty02);
		menuKey = MediaPlayer.create(this, R.raw.kitty01);

		tv_mainPanel = (TextView) findViewById(R.id.displayPanel);
		tv_mainPanel.setText("0");
		tv_subPanel = (TextView) findViewById(R.id.subPanel);
		tv_subPanel.setText("");
		tv_copyPanel = (TextView) findViewById(R.id.copyPanel);

		calc = new Calcurator();

		/*-----メニューを開いたり閉じたりするための指定--------------*/
		// アニメーションをかけるレイアウト部分を指定
		menu = (LinearLayout) findViewById(R.id.menu);
		menulayout = (LinearLayout) findViewById(R.id.menu_layout);

		// このアニメーションファイルを使いますの宣言
		open = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.menu_open);
		close = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.menu_close);

		// メニュー内のボタンは宣言
		btn_How_to = (ImageButton) findViewById(R.id.btn_howTo);
		btn_How_to.setOnClickListener(new HowToClickListener());
		btn_MoreApps = (ImageButton) findViewById(R.id.btn_moreApps);
		btn_MoreApps.setOnClickListener(new MoreAppsListener());
		btn_StartOtoku = (ImageButton) findViewById(R.id.btn_start_otokuApps);
		btn_StartOtoku.setOnClickListener(new StartOtokuListener());

		ImageButton btn_menuBar = (ImageButton) findViewById(R.id.btn_menuBar);
		btn_menuBar.setOnClickListener(new MenuBarOnClickListener());

		// これがないと下のボタンを検知してまう。
		CancelTouchListener cancel = new CancelTouchListener();
		menu.setOnTouchListener(cancel);
		// readPreferences();
	}
	
	class CancelTouchListener implements OnTouchListener {
		public boolean onTouch(View v, MotionEvent event) {
			return true;
		}
	}
	
	class HowToClickListener implements OnClickListener {
		public void onClick(View v) {
			howto.start();
			LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

			View layout = inflater.inflate(R.layout.alertdialog,
					(ViewGroup) findViewById(R.id.layout_root));
			TextView text = (TextView) layout.findViewById(R.id.text);
			ImageView image = (ImageView) layout.findViewById(R.id.image);
			image.setImageResource(R.drawable.ic_cat);
			text.setText("まだ作りかけですニャ");

			new AlertDialog.Builder(MainActivity.this)
					.setView(layout)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}).show();
		}
	}
	
	class MoreAppsListener implements OnClickListener {
		public void onClick(View v) {
			howto.start();
			LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

			View layout = inflater.inflate(R.layout.alertdialog,
					(ViewGroup) findViewById(R.id.layout_root));
			TextView text = (TextView) layout.findViewById(R.id.text);
			ImageView image = (ImageView) layout.findViewById(R.id.image);
			image.setImageResource(R.drawable.ic_cat);

			new AlertDialog.Builder(MainActivity.this)
					.setView(layout)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}).show();
		}
	}
	
	class StartOtokuListener implements OnClickListener {
		public void onClick(View v) {
			howto.start();
			LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

			View layout = inflater.inflate(R.layout.alertdialog,
					(ViewGroup) findViewById(R.id.layout_root));
			//TextView text = (TextView) layout.findViewById(R.id.text);
			ImageView image = (ImageView) layout.findViewById(R.id.image);
			image.setImageResource(R.drawable.ic_cat);

			new AlertDialog.Builder(MainActivity.this)
					.setView(layout)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							}).show();
		}
	}

	class MenuBarOnClickListener implements OnClickListener {
		public void onClick(View v) {
			cover.start();
			MenuBarAnimationListener anim = new MenuBarAnimationListener();
			if (menu.getVisibility() == View.GONE) {
				anim.open(menulayout, menu, open, btn_StartOtoku);

			} else if (menu.getVisibility() == View.VISIBLE) {
				anim.close(menulayout, menu, close, btn_StartOtoku);
			} else {
				Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/*-------------------------------------------------------------------------------------*/
	/* 電卓パネルへの表示用 */
	/*-------------------------------------------------------------------------------------*/
	private void setPanel(String strNum, TextView v) {
		// Formatter formatter = new Formatter();
		// String fText = formatter.setPanel(strNum);

		/*
		 * if (fText == "error") Toast.makeText(MainActivity.this,
		 * "にゃんだかエラーにゃ。", 1000).show(); else v.setText(fText);
		 */
		v.setText(strNum);
	}

	/*-------------------------------------------------------------------------------------*/
	/* ここからボタンの処理 */
	/*-------------------------------------------------------------------------------------*/
	public void NumKeyOnClick(View v) {
		numKey.start();
		// サブパネルの最後が=だった場合は式をクリアする
		if (calc.tv_sub.indexOf("=") == calc.tv_sub.length() - 1) {
			tv_subPanel.setText("");
		}

		String strInKey = getNum(v);
		calc.doInputNum(strInKey);
		setPanel(calc.tv_main, tv_mainPanel);
	}

	private String getNum(View v) {
		String btnName = getBaseContext().getResources().getResourceEntryName(
				v.getId());

		if (btnName.equals("numDot")) {
			return ".";
		} else {
			int index = btnName.indexOf("num");
			index += "num".length();
			return btnName.substring(index);
		}
	}

	/*-------------------------------------------------------------------------------------*/
	public void PanelKeyOnClick(View v) {
		menuKey.start();
		String result = null;
		switch (v.getId()) {

		case R.id.keypadAC:
			calc.allClear();
			tv_copyPanel.setText("今はカラだよ");
			tv_mainPanel.setText("");
			tv_subPanel.setText("");
			break;

		case R.id.keypadCLR:
			calc.doClear();
			tv_mainPanel.setText(calc.tv_main);
			break;

		case R.id.keypadCopy:
			result = calc.doCopy(tv_copyPanel.getText().toString());
			if (result.equals("changeBtn")) {
				((ImageButton) findViewById(R.id.keypadCopy))
						.setBackgroundResource(R.drawable.btn_calc_paste);
			} else {
				setPanel(calc.tv_main, tv_mainPanel);
				((ImageButton) findViewById(R.id.keypadCopy))
						.setBackgroundResource(R.drawable.btn_calc_copy);
			}
			setPanel(calc.tv_copy, tv_copyPanel);
			return;
		}
	}

	// Menu
	public void MenuKeyOnClick(View v) {
		menuKey.start();
		String result = null;
		switch (v.getId()) {

		case R.id.menuBS:
			setPanel(calc.doBackSpace(), tv_mainPanel);
			break;

		case R.id.menuChange:
			setPanel(calc.doChange(), tv_mainPanel);
			break;

		case R.id.menuRepeat:
			result = calc.doRepeat();
			if (result == "ERROR") {
				break;
			} else {
				setPanel(result, tv_mainPanel);
				setPanel(calc.tv_sub, tv_subPanel);
			}
			break;

		case R.id.menuPercent:
			result = calc.doPercent();
			if (result == "ERROR") {
				break;
			} else {
				setPanel(result, tv_mainPanel);
				setPanel(calc.tv_sub, tv_subPanel);
			}
			break;
		}
	}

	/*-------------------------------------------------------------------------------------*/
	// operatorSign
	public void OpeKeyOnClick(View v) {
		menuKey.start();
		switch (v.getId()) {

		case R.id.calcEq:
			calc.doEq();
			setPanel(calc.tv_main, tv_mainPanel);
			setPanel(calc.tv_sub, tv_subPanel);
			break;

		case R.id.calcAdd:
		case R.id.calcSub:
		case R.id.calcMulti:
		case R.id.calcDiv:
			calc.doInputOpe(v);
			setPanel(calc.tv_main, tv_mainPanel);
			setPanel(calc.tv_sub, tv_subPanel);
			break;

		default:
			setPanel(calc.tv_main, tv_mainPanel);
			setPanel(calc.tv_sub, tv_subPanel);
			break;
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * ----------- ホームを押した・電話がかかってきたなど突然終了された場合に数字を プリファレンスに書き込む
	 * ------------------
	 * ------------------------------------------------------------------- void
	 * readPreferences() { SharedPreferences prefs =
	 * getSharedPreferences("CalcInputtings", MODE_PRIVATE); // 第二引数はデフォルト値
	 * tv_mainPanel.setText(prefs.getString("mainPanelTemp", "0"));
	 * tv_subPanel.setText(prefs.getString("subPanelTemp", ""));
	 * tv_copyPanel.setText(prefs.getString("subCopyTemp", "")); }
	 * 
	 * protected void onStop() { super.onStop(); writePreferences(); }
	 * 
	 * void writePreferences() { SharedPreferences prefs =
	 * getSharedPreferences("CalcInputtings", MODE_PRIVATE);
	 * SharedPreferences.Editor editor = prefs.edit();
	 * editor.putString("mainPanelTemp", tv_mainPanel.getText().toString());
	 * editor.putString("subPanelTemp", tv_subPanel.getText().toString());
	 * editor.putString("subCopyTemp", tv_copyPanel.getText().toString());
	 * editor.commit(); }
	 */
}