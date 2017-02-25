package com.xxx.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by lousx on 2016/8/3.
 */
public class DrawableUtil {

    // 获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 大于20K压缩
     *
     * @param baos
     * @return
     */
    public static ByteArrayOutputStream compressImage(ByteArrayOutputStream baos) {
        if (baos.toByteArray().length / 1024 > 200) {
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            bitmap.recycle();
            return compressImage(baos);
        } else {
            return baos;
        }
    }

    /**
     * 多线程压缩图片的质量
     *
     * @param bitmap  内存中的图片
     * @param imgPath 图片的保存路径
     * @author JPH
     * @date 2014-12-5下午11:30:43
     */
    public static void compressImgByQuality(final Bitmap bitmap, final String imgPath, final String newPath, final Handler handler, final int nFileSize) {
        if (bitmap == null) {
            return;
        }
        new Thread(new Runnable() {//开启多线程进行压缩处理
            @Override
            public void run() {
                // TODO Auto-generated method stub
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
                while (baos.toByteArray().length / 1024 > nFileSize) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩
                    baos.reset();//重置baos即让下一次的写入覆盖之前的内容
                    options -= 10;//图片质量每次减少10
                    if (options < 0) options = 0;//如果图片质量小于10，则将图片的质量压缩到最小值
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
                    if (options == 0) break;//如果图片的质量已降到最低则，不再进行压缩
                }
                try {
                    FileOutputStream fos = new FileOutputStream(new File(newPath));//将压缩后的图片保存的本地上指定路径中
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();

                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("obj",newPath);
                    bundle.putString("src",imgPath);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 多线程压缩图片的质量
     *
     * @param bitmap  内存中的图片
     * @param imgPath 图片的保存路径
     * @author JPH
     * @date 2014-12-5下午11:30:43
     */
    public static void compressImageByQuality(final Bitmap bitmap, final String imgPath, final Handler handlers, final int nFileSize) {
        new Thread(new Runnable() {//开启多线程进行压缩处理
            @Override
            public void run() {
                // TODO Auto-generated method stub
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
                while (baos.toByteArray().length / 1024 > nFileSize) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩
                    baos.reset();//重置baos即让下一次的写入覆盖之前的内容
                    options -= 10;//图片质量每次减少10
                    if (options < 0) options = 0;//如果图片质量小于10，则将图片的质量压缩到最小值
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
                    if (options == 0) break;//如果图片的质量已降到最低则，不再进行压缩
                }
                try {
                    FileOutputStream fos = new FileOutputStream(new File(imgPath));//将压缩后的图片保存的本地上指定路径中
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();

                    handlers.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 按比例缩小图片的像素以达到压缩的目的
     * 先按尺寸再按质量压
     *
     * @param imgPath
     * @author JPH
     * @date 2014-12-5下午11:30:59
     */
    public static void compressImageByPixel(String imgPath, Handler handlers, int nFileSize) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;
        float maxSize = 1000f;//默认1000px
        int be = 1;
        if (width > height && width > maxSize) {//缩放比,用高或者宽其中较大的一个数据进行计算
            be = (int) (newOpts.outWidth / maxSize);
        } else if (width < height && height > maxSize) {
            be = (int) (newOpts.outHeight / maxSize);
        }
        be++;
        newOpts.inSampleSize = be;//设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        compressImageByQuality(bitmap, imgPath, handlers, nFileSize);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 按比例缩小图片的像素以达到压缩的目的
     * 先按尺寸再按质量压
     *
     * @param imgPath
     * @author JPH
     * @date 2014-12-5下午11:30:59
     */
    public static void compressImgByPixel(String imgPath, String newPath, Handler handler, int nFileSize) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;
        float maxSize = 1000f;//默认1000px
        int be = 1;
        if (width > height && width > maxSize) {//缩放比,用高或者宽其中较大的一个数据进行计算
            be = (int) (newOpts.outWidth / maxSize);
        } else if (width < height && height > maxSize) {
            be = (int) (newOpts.outHeight / maxSize);
        }
        be++;
        newOpts.inSampleSize = be;//设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        compressImgByQuality(bitmap,imgPath ,newPath, handler, nFileSize);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 按比例缩小图片的像素以达到压缩的目的
     * 先按尺寸再按质量压
     *
     * @param srcPath 源文件
     * @param imgPath
     * @author JPH
     * @date 2014-12-5下午11:30:59
     */
    public static void compressImageByPixel(String srcPath, String imgPath, Handler handlers, int nFileSize) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;
        float maxSize = 1000f;//默认1000px
        int be = 1;
        if (width > height && width > maxSize) {//缩放比,用高或者宽其中较大的一个数据进行计算
            be = (int) (newOpts.outWidth / maxSize);
        } else if (width < height && height > maxSize) {
            be = (int) (newOpts.outHeight / maxSize);
        }
        be++;
        newOpts.inSampleSize = be;//设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        compressImageByQuality(bitmap, imgPath, handlers, nFileSize);//压缩好比例大小后再进行质量压缩
    }

    public static Bitmap decodeUriAsBitmap(Context mContext, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext
                    .getContentResolver(), uri);
        } catch (Exception e) {
        }
        return bitmap;
    }
}
