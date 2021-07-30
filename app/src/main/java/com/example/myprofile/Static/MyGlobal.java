package com.example.myprofile.Static;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyGlobal extends AppCompatActivity {

    private Context context;

    public MyGlobal(Context context) {
        this.context = context;
    }

    public void toast(String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }

    public void  moveActivity(Context context1,Class c){
        Intent intent = new Intent(context1,c);
        startActivity(intent);

    }

    public String getAlphaNumericString(){
        int n=5;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i=0;i<n;i++) {
            int index = (int)(AlphaNumericString.length()*Math.random());
            sb.append(AlphaNumericString
                    .charAt(index)); }

        return sb.toString();
    }

    public boolean emailValidate(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    public static String givePostTime(long diff) {

        long tm[]= {12*30*24*3600,30*24*3600,7*24*3600,24*3600,3600,60,1};
        String ts[] = {"year","month","week","day","hours","minutes","seconds"};
        long ans=0;


        int i=0;
        int n=tm.length;
        while(i<n) {
            ans=diff/tm[i];
            //System.out.println(ans);
            if(ans!=0) {
                break;
            }
            i++;
        }
        String ansStr=Long.valueOf(ans).toString()+" "+ts[i];
        return ansStr;
    }



}
