package com.todos.communicatetogithubapi

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class FetchUserTask(mainActivity: MainActivity) : AsyncTask<String, Void, String?>() {

    private val activity: MainActivity = mainActivity

    /*
     * update the UI by using MainActivity: it is necessary.
     * Create a field that represents a reference to MainActivity.
     * get the reference to the MainActivity frequently
     */
    private val activityRef: WeakReference<MainActivity> = WeakReference(mainActivity)



    /*
     * connect to web page, read the content, collect data as a string
     * @return a string, concatenation of the data
     */
    override fun doInBackground(vararg p0: String): String? {

        val userName = p0[0]
        val userJson: String = fetchUserJson(userName)

        return userJson

        //return getWebPageContent()

    }



    /*
     * the result of the task did in background function is sent as
     * parameter to this function.
     * this function will send the result to the mainActivity
     */
    override fun onPostExecute(userStr: String?) {

        super.onPostExecute(userStr)

        if (userStr == null || userStr.isEmpty())
            activity.displayErrorMessage()
        else
            activity.displayUser(userStr, false)


        // get the MainActivity UI update
        val activity: MainActivity = activityRef.get() ?: return
    }



    /*
     * Retreive a string from the gitHub. actually, that string represents the
     * user.
     * @return a string, a concatenation off all the web page data
     */
    private fun fetchUserJson(userName: String): String {
        val urlTest = "https://mangakakalot.com/manga/haru_matsu_bokura_2" + userName
        val urlStr = "https://api.github.com/users/" + userName
        val url = URL(urlStr)
        var responseStr = ""

        with(url.openConnection() as HttpURLConnection) {
            try {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    responseStr = readResponse(BufferedReader(InputStreamReader(inputStream)))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                println( "\n ERROR => private fun fetchUserJson(userName: String): String { \n " + e.printStackTrace())
            }
        }

        return responseStr
    }



    // to test an internet connexion
    private fun getWebPageContent(): String {

        val urlStr = "https://mangakakalot.com/manga/haru_matsu_bokura_2"
        val url = URL(urlStr)
        var responseStr = ""

        with(url.openConnection() as HttpURLConnection) {
            try {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    responseStr = readResponse(BufferedReader(InputStreamReader(inputStream)))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                println( "\n ERROR => private fun fetchUserJson(userName: String): String { \n " + e.printStackTrace())
            }
        }

        return responseStr
    }



    private fun readResponse(bufferedReader: BufferedReader): String {

        val response = StringBuffer()
        var inputLine = bufferedReader.readLine()

        while (inputLine != null) {

            response.append(inputLine)
            inputLine = bufferedReader.readLine()

        }

        return response.toString()
    }
}
