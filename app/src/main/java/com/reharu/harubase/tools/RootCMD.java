package com.reharu.harubase.tools;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by 星野悠 on 2017/5/31.
 */

public class RootCMD {

    private static Process process = null;
    private static DataOutputStream os = null;
    private static Runtime runtime  = Runtime.getRuntime();
    static {
        try {
            process = Runtime.getRuntime().exec("su") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        os = new DataOutputStream(process.getOutputStream());
    }
    public synchronized static boolean execute(String cmd){
        if(process == null){
            return  false;
        }
        try{
            os.writeBytes(cmd+ "\n");
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
