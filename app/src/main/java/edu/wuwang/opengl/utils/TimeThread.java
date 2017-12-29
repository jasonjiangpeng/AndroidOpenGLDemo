package edu.wuwang.opengl.utils;

import android.os.SystemClock;


/**
 * Created by Administrator on 2017/12/28.
 */

public class TimeThread extends  Thread {
    private  int time;
    private boolean isRun=false;
    private WorkThing workThing;
    public TimeThread(int time) {
        this.time=time;
    }

    public WorkThing getWorkThing() {
        return workThing;
    }

    public void setWorkThing(WorkThing workThing) {
        this.workThing = workThing;
    }

    public TimeThread(int time, WorkThing workThing) {
        this.time=time;
        this.workThing=workThing;
    }
    @Override
    public void run() {
        while (isRun){
            synchronized (this){
                long start=System.currentTimeMillis();
                if (workThing!=null){
                    workThing.startwork();
                }
                long end=time-System.currentTimeMillis()-start;
                if (end>0){
                    SystemClock.sleep(end);
                }

            }
        }

    }
    public void onstart(){
        isRun=true;
        this.start();
    }
    public void ondestory(){
        isRun=false;
    }
    public interface  WorkThing{
        void startwork();

    }

}
