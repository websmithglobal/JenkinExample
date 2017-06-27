package com.ornate.cameraapp;


import android.content.Context;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

import java.io.File;


public class OnSwipeTouchListener implements OnTouchListener {

	@NonNull
	private final GestureDetector gestureDetector;

	Context context;
	View view;
	int id;
	File root = Environment.getExternalStorageDirectory();
	@NonNull
	File cameraAppDir=new File(root.getAbsolutePath()+"/CameraApp");
	ResetImage resetImage;

	@SuppressWarnings("static-access")
	public OnSwipeTouchListener(Context ctx, View v,int id,ResetImage resetImage) {
		context = ctx;
		this.view = v;
		this.id=id;

		gestureDetector = new GestureDetector(ctx, new GestureListener(context));

		this.resetImage = resetImage;
	}

	private final class GestureListener implements GestureDetector.OnDoubleTapListener,GestureDetector.OnGestureListener {

		private static final int SWIPE_THRESHOLD = 100;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;
		private Context context;
		public GestureListener(Context context){
			this.context = context;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent motionEvent) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent motionEvent) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent motionEvent) {

		}

		@Override
		public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX,
							   float velocityY) {
			boolean result = false;
			try {
				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();
				if (Math.abs(diffX) > Math.abs(diffY)) {
					if (Math.abs(diffX) > SWIPE_THRESHOLD
							&& Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffX > 0) {
							TranslateAnimation hide = new TranslateAnimation(0,
									view.getWidth(), 0, 0);
							hide.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {

									File file=new File(cameraAppDir.getAbsolutePath(),id+".jpg");
									file.delete();
									for(int i=(id+1);i<4;i++){
										file=new File(cameraAppDir.getAbsolutePath(),i+".jpg");
										File newfile=new File(cameraAppDir.getAbsolutePath(),(i-1)+".jpg");
										file.renameTo(newfile);
									}
									if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE  && cameraAppDir.listFiles().length ==3){
										view.setVisibility(View.GONE);
										Intent intent=new Intent(context,Compare_Screen.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										context.startActivity(intent);
									}
									else {
										moveCamera();
									}


								}
							});
							hide.setDuration(500);
							view.startAnimation(hide);


							// onSwipeRight();
						} else {
							TranslateAnimation hide = new TranslateAnimation(0,
									view.getWidth(), 0, 0);
							hide.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {

									File file=new File(cameraAppDir.getAbsolutePath(),id+".jpg");
									file.delete();
									for(int i=(id+1);i<4;i++){
										file=new File(cameraAppDir.getAbsolutePath(),i+".jpg");
										File newfile=new File(cameraAppDir.getAbsolutePath(),(i-1)+".jpg");
										file.renameTo(newfile);
									}
									if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE  && cameraAppDir.listFiles().length ==3){
										view.setVisibility(View.GONE);
										Intent intent=new Intent(context,Compare_Screen.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										context.startActivity(intent);
									}
									else {
										moveCamera();
									}


								}
							});
							hide.setDuration(500);
							view.startAnimation(hide);

							// onSwipeLeft();
						}
					}
				} else {
					if (Math.abs(diffY) > SWIPE_THRESHOLD
							&& Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffY > 0) {
							TranslateAnimation hide = new TranslateAnimation(0,
									view.getWidth(), 0, 0);
							hide.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {


									File file=new File(cameraAppDir.getAbsolutePath(),id+".jpg");
									file.delete();
									for(int i=(id+1);i<4;i++){
										file=new File(cameraAppDir.getAbsolutePath(),i+".jpg");
										File newfile=new File(cameraAppDir.getAbsolutePath(),(i-1)+".jpg");
										file.renameTo(newfile);
									}
									if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE  && cameraAppDir.listFiles().length ==3){
										view.setVisibility(View.GONE);
										Intent intent=new Intent(context,Compare_Screen.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										context.startActivity(intent);
									}
									else {
										moveCamera();
									}

								}
							});
							hide.setDuration(500);
							view.startAnimation(hide);


						} else {
							TranslateAnimation hide = new TranslateAnimation(0,
									view.getWidth(), 0, 0);
							hide.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {

									File file=new File(cameraAppDir.getAbsolutePath(),id+".jpg");
									file.delete();
									if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE  && cameraAppDir.listFiles().length==3){
										view.setVisibility(View.GONE);
										Intent intent=new Intent(context,Compare_Screen.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										context.startActivity(intent);
									}
									else {
										moveCamera();
									}

								}
							});
							hide.setDuration(500);
							view.startAnimation(hide);

						}
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return result;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
			return false;
		}

		@Override
		public boolean onDoubleTap(MotionEvent motionEvent) {
			Intent intent = new Intent(context,ZoomScreen.class);
			intent.putExtra("img", new File(cameraAppDir.getAbsolutePath(), id+".jpg").getAbsolutePath());
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent motionEvent) {
			Intent intent = new Intent(context,ZoomScreen.class);
			intent.putExtra("img", new File(cameraAppDir.getAbsolutePath(), id+".jpg").getAbsolutePath());
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return false;
		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {

		return gestureDetector.onTouchEvent(event);
		// return false;
	}

	public void moveCamera(){




			resetImage.resetImage();






		if(cameraAppDir.listFiles().length==1) {
			Intent intent = new Intent(context,AndroidSurfaceviewExample.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}




}