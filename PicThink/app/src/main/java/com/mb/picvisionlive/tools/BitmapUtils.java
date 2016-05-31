package com.mb.picvisionlive.tools;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by wm on 2015/11/25 0025.
 */
public class BitmapUtils {

    static boolean  saveBitmap2file(Bitmap bmp, String filename){
        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;//100 就是原质量
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/" + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return bmp.compress(format, quality, stream);
    }

    public static boolean  saveBitmapTofile(Bitmap bmp, String filename){
        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;//100 就是原质量
        OutputStream stream = null;
        try {
            String savePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            stream = new FileOutputStream(savePath + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return bmp.compress(format, quality, stream);
    }

    public static File getFileFromLocal(String fileName) {
        File tempFile =new File(fileName.trim());
        return tempFile;
    }



   /* public void saveFile(Context context,Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() +"/revoeye/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }*/
}
