package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.mb.picvisionlive.tools.FastBlur;
import com.tencent.qcloud.suixinbo.views.HomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PersonInfoFragment extends Fragment {
    Context context;
    //    @Bind(R.id.fragment_person_info_bg_img)
    ImageView infoBgImg;

    int width_img = 0;
    int heigh_img = 0;
    @Bind(R.id.fragment_person_info_goEdit_img)
    ImageView goEdit_img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_person_info, null);
        ButterKnife.bind(this, view);

        infoBgImg = (ImageView) view.findViewById(R.id.fragment_person_info_bg_img);
        initBgImageView();

        return view;
    }

    private void initBgImageView() {
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.test3);

        infoBgImg.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        if (width_img == 0) {
                            width_img = infoBgImg.getWidth();
                            heigh_img = infoBgImg.getHeight();
                            Log.e("===width_img===", width_img + "");
                            Log.e("===heigh_imgt===", heigh_img + "");
                        }
                        //infoBgImg.setImageBitmap(resizeImage(bitmap, width_img, heigh_img));
                        Bitmap map = resizeImage(bitmap, width_img, heigh_img);
                        blur(map, infoBgImg);
                        return true;
                    }
                });
    }

    private void blur(Bitmap bkg, ImageView view) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 8;//8
        float radius = 2;//2

//        Bitmap overlay = Bitmap.createBitmap(
//                (int) (view.getMeasuredWidth() / scaleFactor),
//                (int) (view.getMeasuredHeight() / scaleFactor),
//                Bitmap.Config.ARGB_8888);

        Bitmap overlay = Bitmap.createBitmap(
                (int) (width_img / scaleFactor),
                (int) (heigh_img / scaleFactor),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()
                / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        //view.setBackgroundDrawable(new BitmapDrawable(getResources(), overlay));
        view.setImageBitmap(overlay);
        //view.setImageBitmap(resizeImage(overlay, width_img, heigh_img));
        System.out.println(System.currentTimeMillis() - startMs + "ms");
    }

    //使用Bitmap加Matrix来缩放
    public Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Log.e("===newWidth===", newWidth + "");
        Log.e("===newHeight===", newHeight + "");

        Log.e("===width===", width + "");
        Log.e("===height===", height + "");

        Log.e("===scaleWidth===", scaleWidth + "");
        Log.e("===scaleHeight===", scaleHeight + "");

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //matrix.postScale(8f, 8f);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        Log.e("===resizedBitmap===", resizedBitmap.getWidth() + "");
        Log.e("===resizedBitmap===", resizedBitmap.getHeight() + "");
        // Drawable daw=new BitmapDrawable(getResources(), resizedBitmap);
        return resizedBitmap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.fragment_person_info_goEdit_img)
    public void onClick() {
        startActivity(new Intent(context, EditPersonInfoActivity.class));
    }
}
