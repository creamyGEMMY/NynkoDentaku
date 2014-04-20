package com.gemmiroid.apps.nyankodentaku;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

//別に一緒に書いてもいいんだけどMainActivityが長くて嫌だったので分けたクラス
public class MenuBarAnimationListener implements AnimationListener {
	private LinearLayout menu;
	private ImageButton btn;
	//今メニューがどうなってるかチェックするヤツー
	private boolean close;
	
	//デフォルトコンストラクタだよ
	public MenuBarAnimationListener(){}
	
	//メニューを開く（上から出てくる）
	public void open(LinearLayout ll,LinearLayout menu,Animation anim,ImageButton btn){
		if(menu.getVisibility() == View.GONE){
			this.menu = menu;
			this.btn		= btn;
			btn.setBackgroundResource(R.drawable.btn_menu_otoku_b);
			anim.setAnimationListener(this);
			menu.setVisibility(View.VISIBLE);
			ll.startAnimation(anim);
		}
	}
	
	//メニューを閉じる（上に消えていく）
	public void close(LinearLayout ll,LinearLayout menu,Animation anim,ImageButton btn) {
		if(menu.getVisibility() == View.VISIBLE){
			this.menu = menu;
			this.btn		= btn;
			btn.setBackgroundResource(R.drawable.btn_menu_otoku_b);
			anim.setAnimationListener(this);
			close = true;
			ll.startAnimation(anim);
			//この行にifを書いたらアニメーション終了検知前に検査され、
			//うまくいかなかったのでonAnimationEndに書くことにしました
		}		
	}
	
	//startAnimationの後の操作が、Animation終了後に行われるようリスナーに書いた
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO 自動生成されたメソッド・スタブ
		if(close){
			menu.setVisibility(View.GONE);
			close = false;
		}else{
			menu.setVisibility(View.VISIBLE);
			btn.setBackgroundResource(R.drawable.selector_top_otoku_btn);
		}
	}
	
	//使わないけどオーバーライドしなきゃダメなメソッド
	@Override
	public void onAnimationRepeat(Animation animation) {
	}
	@Override
	public void onAnimationStart(Animation animation) {
	}

}
