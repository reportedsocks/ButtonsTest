package com.reportedsocks.buttonstest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.reportedsocks.buttonstest.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ImageView[] imageViews;
    private int[] wasImageClickedArray;
    private int[] images;
    private int[] imagesClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wasImageClickedArray = new int[]{0,0,0,0,0};

        images = new int[5];
        images[0] = R.drawable.tb_ic_menu;
        images[1] = R.drawable.tb_ic_shop;
        images[2] = R.drawable.tb_ic_pause;
        images[3] = R.drawable.tb_ic_normal;
        images[4] = R.drawable.tb_ic_accelerated;

        imagesClicked = new int[5];
        imagesClicked[0] = R.drawable.tb_ic_menu_clicked;
        imagesClicked[1] = R.drawable.tb_ic_shop_clicked;
        imagesClicked[2] = R.drawable.tb_ic_pause_clicked;
        imagesClicked[3] = R.drawable.tb_ic_normal_clicked;
        imagesClicked[4] = R.drawable.tb_ic_accelerated_clicked;

        imageViews = new ImageView[5];
        imageViews[0] = binding.menuImage;
        imageViews[1] = binding.shopImage;
        imageViews[2] = binding.pauseImage;
        imageViews[3] = binding.playImage;
        imageViews[4] = binding.acceleratedImage;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int[] viewLocationArray = new int[2];
        int eventPadTouch = event.getAction();
        // get coordinates of user click
        float eventX = event.getX();
        float eventY = event.getY();

        switch (eventPadTouch) {

            case MotionEvent.ACTION_DOWN:
                for( int i = 0; i < imageViews.length; i++ ){

                    // get coordinates of ImageView position
                    // then rest them from coordinates of user click
                    // this gives coordinates of click on Image itself
                    imageViews[i].getLocationInWindow(viewLocationArray);
                    float X = eventX - viewLocationArray[0];
                    float Y = eventY - viewLocationArray[1];

                    //decode bitmap of image
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images[i]);

                    if ( X >= 0 & Y >= 0
                            & X < bitmap.getWidth() & Y < bitmap.getHeight() ){
                        // if user didn't click on transparent part, pixel won't be zero
                        if ( bitmap.getPixel((int) X, (int) Y) != 0) {
                            int pos = i+1;
                            Log.d("MyLogs", "clicked button " + pos);
                            Toast.makeText(this,
                                    "Button clicked: " + pos, Toast.LENGTH_SHORT).show();
                            imageViews[i].setImageResource(imagesClicked[i]);
                            wasImageClickedArray[i] = 1;
                            return true;
                        }
                    }
                }
                return false;
            case MotionEvent.ACTION_UP:
                // if any ImageView was marked as clicked, set normal image to it
                for( int i = 0; i < wasImageClickedArray.length; i++ ){
                    if( wasImageClickedArray[i] == 1 ){
                        imageViews[i].setImageResource(images[i]);
                        wasImageClickedArray[i] = 0;
                        return true;
                    }
                }
                return true;
            default:
                return false;
        }
    }
}
