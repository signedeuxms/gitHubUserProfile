package com.todos.communicatetogithubapi

import android.os.AsyncTask
import javax.net.ssl.HttpsURLConnection
import java.net.HttpURLConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.InputStream
import java.net.URL


class LoadImageTask(mainActivity: MainActivity) : AsyncTask<String, Void, Bitmap?>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }


    override fun doInBackground(vararg p0: String): Bitmap? {

        try{
            println( " \n Bitmap-1-p0-1 \t $p0")
            var loc_url: URL? = URL( p0[0] );
            var connection: HttpsURLConnection? = loc_url?.openConnection() as HttpsURLConnection

            connection?.setDoInput(true);
            connection?.connect();

            var inStr: InputStream? = connection?.inputStream
            var bmp: Bitmap? = BitmapFactory.decodeStream(inStr)
            return bmp;

        }catch (exp: Exception){
            println( " \n Bitmap-2")
            Log.e("error-bitmap => ", exp.message)
            exp.printStackTrace()
            return null;
        }
    }


    override fun onPostExecute(aDouble: Bitmap? ) {
        super.onPostExecute(aDouble);
    }

}