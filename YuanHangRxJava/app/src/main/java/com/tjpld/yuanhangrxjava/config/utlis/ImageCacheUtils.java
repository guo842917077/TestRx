package com.tjpld.yuanhangrxjava.config.utlis;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * 图片缓存工具类
 * 强引用 保存图片 以对象的形式进行引用
 * 软引用负责移除图片 引用一个对象，当内存不足时，除了自己得引用对象没有其他引用对象时，被gc回收
 */
public class ImageCacheUtils {
    private static LruCache<String, Bitmap> mLruCache;//强引用缓存
    private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache;//弱引用缓存
    private static final int LRU_CACHE_SIZE=4*1024*1024;//强引用缓存的大小
    //也可以通过---》Runtime.getRuntime().maxMemory()/8;
    private static final int SOFT_CACHE_SIZE=20;//弱引用缓存的个数
    public ImageCacheUtils(){
        mLruCache=new LruCache<String,Bitmap>(LRU_CACHE_SIZE){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                if(value!=null)
                    return value.getRowBytes()*value.getHeight();
                else
                    return 0;
            }
            //得到不长时间使用的对象
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if(oldValue!=null){
                    //将不长时间使用的对象交给弱引用
                    mSoftCache.put(key,new SoftReference<Bitmap>(oldValue));
                }
            }
        };
        //初始化弱引用
        mSoftCache=new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_SIZE,0.75f,true){
            private static final long serialVersionUID = 1L;
            //当软引用的内存大于给定值是 老的数据将会被移除

            @Override
            protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
                if(size()>SOFT_CACHE_SIZE){
                    //移出掉老的数据
                    return true;
                }
                    return false;
            }
        };
    }
    /**
     * 从缓存中获取图片  对于强引用每次找到一个图片时 都把图片放到链表的最顶端
     * 代表被使用过
     *
     */
    public Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap;
        // 先从强引用缓存中获取
        synchronized (mLruCache) {
            bitmap = mLruCache.get(url);
            if (bitmap != null) {
                // 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
                mLruCache.remove(url);
                mSoftCache.put(url,new SoftReference<Bitmap>(bitmap));
                return bitmap;
            }
        }

        // 如果强引用缓存中找不到，到软引用缓存中找，找到后就把它从软引用中移到强引用缓存中
        synchronized (mSoftCache) {
            SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
            if (bitmapReference != null) {
                bitmap = bitmapReference.get();
                if (bitmap != null) {
                    // 将图片移回LruCache
                    mLruCache.put(url, bitmap);
                    mSoftCache.remove(url);
                    return bitmap;
                } else {
                    mSoftCache.remove(url);
                }
            }
        }
        return null;
    }
    /**
     * 添加图片到缓存
     */
    public void addBitmapToMemory(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (mLruCache) {
                mLruCache.put(url, bitmap);
            }
        }
    }
    //清除不常用的图片
    public void clearCache() {
        mSoftCache.clear();
    }
    //得到不长用的缓存的个数
    public int getOldCacheSize(){
        return mSoftCache.size();
    }
}
