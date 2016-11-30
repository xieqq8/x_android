package com.xxx.utils.DownloadFile;

/**
 * Created by xieqq on 2015-10-23 .
 */

import com.xxx.utils.LogX;
import com.xxx.xbase.XxErrorCode;
import com.xxx.xbase.XxException;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * 下载线程
 */
class DownloadThread implements Runnable {
    private final int bufferSize = 1024 * 100;//缓冲区大小
    private String fileName;//存储在本地的文件名称
    private String filePath;//存储的路径
    private File[] tempFiles;//保存thread的下载进度缓存文件的集合
    private int threadCount;//下载的线程数
    private URL uri;
    private volatile boolean error = false;//全局变量，使用volatile同步，下载产生异常时改变
    private long totalReadSize = 0;//已读取的文件大小
    private DownloadFileCallback callback;//下载的回调接口

    public void setRunParm(String fileName
            , String filePath
            , File[] tempFiles
            , int threadCount
            , URL uri
            , DownloadFileCallback callback) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.tempFiles = tempFiles;
        this.threadCount = threadCount;
        this.uri = uri;
        this.callback = callback;

        tempFile = new File(filePath + "/thread" + threadId, fileName.replaceAll(".apk", ".position"));
        tempFiles[threadId - 1] = tempFile;
        if (tempFile.exists()) {
            isFirst = false;
            readPositionInfo(); // 读出之前己下载的数
        } else {
            // 第一次下载
            isFirst = true;

            tempFile.getParentFile().mkdirs();
//            startPositions = new long[threadCount];
//            endPositions = new long[threadCount];
            oldStartPostions = 0;
        }
    }

    public long getTotalReadSize() {
        return totalReadSize;
    }

    private int threadId;
    private long startPosition; // 开始位置
    private long endPosition;   // 结束位置
    private RandomAccessFile randomAccessFile;
    private CountDownLatch countDownLatch;
    private boolean isFirst = true;
    //    private long[] startPositions;  // 记录的之前的
    private long oldStartPostions;
//    private long[] endPositions;

    private File tempFile;


    /**
     * @param threadId         把一下载拆成多个任务下载
     * @param startPosition    开始位置
     * @param endPosition      结束位置
     * @param randomAccessFile
     * @param countDownLatch
     */
    public DownloadThread(int threadId, long startPosition, long endPosition, RandomAccessFile randomAccessFile, CountDownLatch countDownLatch) {
        this.threadId = threadId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.randomAccessFile = randomAccessFile;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");// 以GET方式连接
//            connection.setRequestProperty("User-agent","Mozilla/5.0 (Linux; Android 4.2.1; Nexus 7 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19");
            connection.setRequestProperty("User-Agent", "NetFox");
            connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            connection.setConnectTimeout(5 * 60 * 1000);// 设置连接超时
            connection.setReadTimeout(60 * 1000);//设置数据读取超时
            connection.setAllowUserInteraction(true);// 允许用户交互
//            if (isFirst) {
            // 下载过oldStartPostions不用 0
//                randomAccessFile.seek(startPosition+oldStartPostions);
//            } else {
            startPosition = startPosition + oldStartPostions;
//            endPosition = endPosition;//endPositions[threadId - 1];
            randomAccessFile.seek(startPosition);
//            }
            connection.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);// 设置每条线程开始下载的位置
            LogX.getLogger().d("thread id %d ,Range bytes=%d  %d", this.threadId, (int) startPosition, (int) endPosition);

            //设置了上面的头信息后，响应码为206代表请求资源成功，而不再是200
            int code = connection.getResponseCode();
            if (code == 206) {
                // 可以断点续传
                LogX.getLogger().d("thread id %d ,code=%d", this.threadId, code);
            } else {
                // code == 200 不能断点续传，重新开始下载
                LogX.getLogger().d("thread id %d ,code=%d", this.threadId, code);
                // 重新开始下载
                randomAccessFile.seek(0);
                totalReadSize = 0;
            }

            InputStream inputStream = new BufferedInputStream(connection.getInputStream(), bufferSize);// 使用缓冲区读取文件
            byte[] b = new byte[bufferSize];
            int len = 0;
            long readSize = startPosition;
            while (!error && (len = inputStream.read(b)) != -1) {
                randomAccessFile.write(b, 0, len);
                totalReadSize += len;
                readSize += len;
                savePositionInfo(readSize, endPosition, totalReadSize);
            }

            inputStream.close();
            randomAccessFile.close();
            connection.disconnect();
            countDownLatch.countDown();// 每条线程执行完之后减一
        } catch (Exception e) {
            error = true;
            callback.handleException(new XxException(XxErrorCode.NETWORK_ERROR,
                    new String[]{"下载失败，ErrorCode: " + XxErrorCode.NETWORK_ERROR.name()}), 200);
//            callback.handleException(new XxException(e.getMessage()),1);// 下载失败的回调
        }
    }

    /**
     * 将每条线程下载的开始和结束位置写入到临时文件中
     */
    private void savePositionInfo(long startPosition, long endPosition, long totalReadSize) {
        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(tempFile));
//            outputStream.writeInt(startPositions.length);
            outputStream.writeInt(threadId);
            outputStream.writeLong(totalReadSize); // 己下载的数
//            for (int i = 0; i < startPositions.length; i++) {
//                outputStream.writeLong(startPosition);
//                outputStream.writeLong(endPosition);
//            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取临时文件
     */
    private void readPositionInfo() {
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(tempFile));
//            int startPositionLength = inputStream.readInt();
            int nthID = inputStream.readInt();
            totalReadSize = inputStream.readLong(); // 己下载这么多
            LogX.getLogger().d("have download %d", (int) totalReadSize);
            oldStartPostions = totalReadSize;// 己下载这么多
//                    startPositions = new long[startPositionLength];
//            endPositions = new long[startPositionLength];
//            for (int i = 0; i < startPositionLength; i++) {
//                startPositions[i] = inputStream.readLong();
//                endPositions[i] = inputStream.readLong();
//            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}