package com.example.Servlet_Connection;

//------------------------------------------------------------------------
//Intensive CPU service running its heavy duty task in an 
//AsyncTask object. Uses 'Message handling' for synchronization.
//computing Fibonacci numbers between 20 & 50 [ O(2^n) ]


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Message;

import java.util.Random;

public class MyService5Async extends Service {
	boolean run =true;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		new GetNumbers().execute();
	}

	public class GetNumbers extends AsyncTask <Integer, Integer, int[]> {
		
		@Override
		protected int[] doInBackground(Integer... params) {
			Random rand = new Random();

			int one = rand.nextInt(1000);
			int two = rand.nextInt(1000);
			int three = rand.nextInt(1000);
			int four = rand.nextInt(1000);
			int five = rand.nextInt(1000);

			int[] array = new int[5];
			array[0] = one;
			array[1] = two;
			array[2] = three;
			array[3] = four;
			array[4] = five;
			try{
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return array;

		}

		@Override
		protected void onPostExecute(int[] ints) {
			super.onPostExecute(ints);

			Intent intentFilter5 = new Intent("NameOfIntent");
			intentFilter5.putExtra("array",ints);
			sendBroadcast(intentFilter5);

		}
	}

}//MyService5
//publishProgress(array[0]);
//return null;
/*@Override
			protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			Intent intentFilter5 = new Intent("NameOfIntent");
			String data = "" + values[0] + ": " + values[1];
			intentFilter5.putExtra("array", data);
			sendBroadcast(intentFilter5);

		}*/
//@Override
//public void onDestroy() {
//super.onDestroy();
//run = false;
//}