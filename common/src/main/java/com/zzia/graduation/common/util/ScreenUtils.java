package com.zzia.graduation.common.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

//获得屏幕的像素
public class ScreenUtils {

	// 返回屏幕宽和高
	public static Screen getScreenPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels,dm.density);
	}
	
	/**
	* 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	*/
	public static int dip2px(Context context, float dpValue) {
	  final float scale = context.getResources().getDisplayMetrics().density;
	  return (int) (dpValue * scale + 0.5f);
	}

	/**
	* 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	*/
	public static int px2dip(Context context, float pxValue) {
	  final float scale = context.getResources().getDisplayMetrics().density;
	  return (int) (pxValue / scale + 0.5f);
	}

	public static class Screen {
		public int widthPixels = 480;
		public int heightPixels = 800;
		public float density = 1.5f;

		public Screen() {
		}

//		public Screen(int widthPixels, int heightPixels) {
//			this.widthPixels = widthPixels;
//			this.heightPixels = heightPixels;
//		}
		

		public Screen(int widthPixels, int heightPixels, float density) {
			this.widthPixels = widthPixels;
			this.heightPixels = heightPixels;
			this.density = density;
		}

		@Override
		public String toString() {
			return "(" + widthPixels + "," + heightPixels + ")";
		}
	}
}
