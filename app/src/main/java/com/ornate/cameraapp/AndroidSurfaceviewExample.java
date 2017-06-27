package com.ornate.cameraapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

@SuppressWarnings("ALL")
public class AndroidSurfaceviewExample extends Activity implements SurfaceHolder.Callback {
	TextView compareImage;

	@SuppressWarnings("deprecation")
	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	Button imgPreview;
	PictureCallback rawCallback;
	ShutterCallback shutterCallback;
	@Nullable
	PictureCallback jpegCallback;
	int count=0;

	File root = Environment.getExternalStorageDirectory();
	@NonNull
	File cameraAppDir=new File(root.getAbsolutePath()+"/CameraApp");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		/*BaseActivity.imageLoader.init(ImageLoaderConfiguration
				.createDefault(getBaseContext()));*/

		count =findImageName();

		imgPreview = (Button) findViewById(R.id.imgPreview);

		compareImage = (TextView) findViewById(R.id.compareImage);
		surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
		surfaceView.getHolder().addCallback(this);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		camera = Camera.open(findFrontFacingCamera());

		jpegCallback = new PictureCallback() {
			public void onPictureTaken(@NonNull byte[] data, @NonNull Camera camera) {
				FileOutputStream outStream = null;
				try {
					File root = Environment.getExternalStorageDirectory();
					File cameraAppDir=new File(root.getAbsolutePath()+"/CameraApp");
					if(!cameraAppDir.exists()){
						cameraAppDir.mkdirs();
					}
					File file=new File(cameraAppDir, count+".jpg");
					outStream = new FileOutputStream(file);

					outStream.write(data);
					outStream.close();
					Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
					final BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					options.inSampleSize = 2;
					options.inJustDecodeBounds = false;
					options.inTempStorage = new byte[16 * 1024];

					Bitmap loadedImage = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
					int w = loadedImage.getWidth();
					int h = loadedImage.getHeight();
					Matrix mtx = new Matrix();
					mtx.postRotate(90);
					loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, w, h, mtx, true);
					imgPreview.setBackgroundDrawable(new BitmapDrawable(getResources(), loadedImage));
					/*String imageUri = "file://"+file.getAbsolutePath();
					BaseActivity.imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							int w = loadedImage.getWidth();
							int h = loadedImage.getHeight();
							Matrix mtx = new Matrix();
							mtx.postRotate(90);
							// Rotating Bitmap
							loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, w, h, mtx, true);
							imgPreview.setBackgroundDrawable(new BitmapDrawable(getResources(), loadedImage));

						}

						@Override
						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

						}
					});*/
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
				Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();
				if(cameraAppDir.listFiles().length<4) {
					count =findImageName();
				}
				else{
					compareImage();
				}
				camera.startPreview();
			}
		};
		imgPreview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (cameraAppDir.listFiles().length > 1) {
					compareImage();
				}
			}
		});
		surfaceView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					captureImage(view);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});



		if(cameraAppDir.exists()) {
			if (cameraAppDir.listFiles().length > 0) {
				File file=null;
				for(int c=0;c<4;c++) {
					File file1 = new File(cameraAppDir.getAbsolutePath(), c + ".jpg");
					if(file1.exists()) {
						file = file1;
						break;
					}
				}

				if(file!=null) {
					if (file.exists()) {

						final BitmapFactory.Options options = new BitmapFactory.Options();
						options.inJustDecodeBounds = true;
						options.inSampleSize = 2;
						options.inJustDecodeBounds = false;
						options.inTempStorage = new byte[16 * 1024];

						Bitmap loadedImage = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
						int w = loadedImage.getWidth();
						int h = loadedImage.getHeight();
						Matrix mtx = new Matrix();
						mtx.postRotate(90);
						loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, w, h, mtx, true);
						imgPreview.setBackgroundDrawable(new BitmapDrawable(getResources(), loadedImage));

						/*final BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 8;*/

					/*	String imageUri = "file://"+file.getAbsolutePath();
						BaseActivity.imageLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
							@Override
							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
								int w = loadedImage.getWidth();
								int h = loadedImage.getHeight();
								Matrix mtx = new Matrix();
								mtx.postRotate(90);
								// Rotating Bitmap
								loadedImage = Bitmap.createBitmap(loadedImage, 0, 0, w, h, mtx, true);
								imgPreview.setBackgroundDrawable(new BitmapDrawable(getResources(), loadedImage));

							}

							@Override
							public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

							}
						});*/
					}
				}
			}
		}
	}

	public static Bitmap resampleImage(String path, int maxDim) throws Exception {
		BitmapFactory.Options bfo = new BitmapFactory.Options();
		bfo.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, bfo);

		BitmapFactory.Options optsDownSample = new BitmapFactory.Options();
		optsDownSample.inSampleSize = getClosestResampleSize(bfo.outWidth, bfo.outHeight, maxDim);

		Bitmap bmpt = BitmapFactory.decodeFile(path, optsDownSample);

		Matrix m = new Matrix();

		if (bmpt.getWidth() > maxDim || bmpt.getHeight() > maxDim) {
			BitmapFactory.Options optsScale = getResampling(bmpt.getWidth(), bmpt.getHeight(), maxDim);
			m.postScale((float) optsScale.outWidth / (float) bmpt.getWidth(), (float) optsScale.outHeight / (float) bmpt.getHeight());
		}

		int sdk = new Integer(Build.VERSION.SDK).intValue();
		if (sdk > 4) {
			int rotation = ExifUtils.getExifRotation(path);
			if (rotation != 0) {
				m.postRotate(rotation);
			}
		}

		return Bitmap.createBitmap(bmpt, 0, 0, bmpt.getWidth(), bmpt.getHeight(), m, true);
	}
	@Nullable
	public  Bitmap decodeFile(@Nullable String path) {//you can provide file path here
		int orientation;
		try {
			if (path == null) {
				return null;
			}
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 0;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale++;
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Bitmap bm = BitmapFactory.decodeFile(path, o2);
			Bitmap bitmap = bm;

			ExifInterface exif = new ExifInterface(path);

			orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

			Log.e("ExifInteface .........", "rotation ="+orientation);

			//exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

			Log.e("orientation", "" + orientation);
			Matrix m = new Matrix();

			if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
				m.postRotate(180);
				//m.postScale((float) bm.getWidth(), (float) bm.getHeight());
				// if(m.preRotate(90)){
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),bm.getHeight(), m, true);
				return bitmap;
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
				m.postRotate(90);
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),bm.getHeight(), m, true);
				return bitmap;
			}
			else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
				m.postRotate(270);
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),bm.getHeight(), m, true);
				return bitmap;
			}
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}
	private static int getClosestResampleSize(int cx, int cy, int maxDim) {
        /*Log.e("cx",""+cx);
        Log.e("cy",""+cy);*/
		int max = Math.max(cx, cy);

		int resample = 1;
		for (resample = 1; resample < Integer.MAX_VALUE; resample++) {
			if (resample * maxDim > max) {
				resample--;
				break;
			}
		}

		if (resample > 0) {
			return resample;
		}
		return 1;
	}


	@NonNull
	private static BitmapFactory.Options getResampling(int cx, int cy, int max) {
		float scaleVal = 1.0f;
		BitmapFactory.Options bfo = new BitmapFactory.Options();
		if (cx > cy) {
			scaleVal = (float) max / (float) cx;
		} else if (cy > cx) {
			scaleVal = (float) max / (float) cy;
		} else {
			scaleVal = (float) max / (float) cx;
		}
		bfo.outWidth = (int) (cx * scaleVal + 0.5f);
		bfo.outHeight = (int) (cy * scaleVal + 0.5f);
		return bfo;
	}

	/**
	 * Multiplies two integers.
	 *
	 * @param x The first integer.
	 * @param y The second integer.
	 * @return The two integers multiplied together.
	 * @see findFrontFacingCamera
	 * @abc hi
	 */
	public void captureImage(View v) throws IOException {
		//take the picture
		/**
		 * use to capture images
		 */
		camera.takePicture(null, null, jpegCallback);
	}
	private int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	public void compareImage(){
		finish();
		Intent intent = new Intent(getApplicationContext(),Compare_Screen.class);
		startActivity(intent);
	}
	@Override
	public void onPause() {
		super.onPause();
		camera.stopPreview();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		camera.release();
		Log.d("CAMERA", "Destroy");
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Camera.Parameters params = camera.getParameters();
		List<Camera.Size> sizes = params.getSupportedPreviewSizes();
		Camera.Size selected = sizes.get(0);
		params.setPreviewSize(selected.width,selected.height);
		camera.setParameters(params);

		camera.setDisplayOrientation(90);
		camera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			camera.setPreviewDisplay(surfaceView.getHolder());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("PREVIEW", "surfaceDestroyed");
	}

	public int findImageName(){
		int a=0;
		if(cameraAppDir.exists()) {
			for(int i=0;i<4;i++) {
				File file = new File(cameraAppDir.getAbsolutePath(), i + ".jpg");
				if (!file.exists()) {
					a = i;
				}
			}
		}
		return a;

	}
}