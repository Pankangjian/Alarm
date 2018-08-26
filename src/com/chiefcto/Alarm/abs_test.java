package com.chiefcto.Alarm;

import android.util.Log;

public abstract class abs_test implements inter_test{
	public void a() {
		Log.d("abs_test", "   test   abs_test");
		c();
	}
	public abstract void b();
	private void c(){
		writei();
		readi();
	}
}
