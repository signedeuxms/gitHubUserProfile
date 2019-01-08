package com.todos.communicatetogithubapi

import android.os.AsyncTask
import android.provider.MediaStore
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
//import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Rfc3339DateJsonAdapter
import java.util.*

class FetchUserTask(mainActivity: MainActivity) : AsyncTask<String, User, User?>() {

    private val activity: MainActivity = mainActivity


    /*
     * update the UI by using MainActivity: it is necessary.
     * Create a field that represents a reference to MainActivity.
     * get the reference to the MainActivity frequently
     */
    private val activityRef: WeakReference<MainActivity> = WeakReference(mainActivity)



    /*
     * connect to web page, read the content, collect data as a string
     *
     * @return an instance of class User
     */
    override fun doInBackground(vararg p0: String): User? {

        val mainActivity: MainActivity = activityRef.get() ?: return null

        // declare interface that make query to the database
        val dao = AppDatabase.getInstance(mainActivity)?.interfaceToDatabase()

        val userName = p0[0]
        var user = dao?.getUserByLogin(userName)

        println( " \n user-dao $user.id \t name $user.name \t avatar $user.avatar_url \t login $user.login ")

        if (!isCancelled) {
            if (user != null) {
                println( " \n publishProgress-1 $user.id \t name $user.name \t avatar $user.avatar_url \t login $user.login ")
                publishProgress(user)
                println( " \n publishProgress-2 $user.id \t name $user.name \t avatar $user.avatar_url \t login $user.login ")
            }

            // get user by github REST
            val userJson: String = fetchUserJson(userName)

            if (!userJson.isEmpty()) {

                println( " \n !isEmpty()-userJson $userJson ")

                val moshi = Moshi.Builder()
                        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
                        .add(DefaultOnDataMismatchAdapter.newFactory(MediaStore.Video::class.java, null)) /* 2 */
                        .add(DefaultOnDataMismatchAdapter.newFactory(User::class.java, null)) /* 5 */
                        .add(KotlinJsonAdapterFactory())
                        .build()
                val adapter = moshi.adapter(User::class.java)
                user = adapter.fromJson(userJson)
                println(  " \n user-adapter => " + adapter.toJson(user)  )

                if (user != null) {
                    println( " \n !isEmpty()-dao1-user $user.id \t name $user.name \t avatar $user.avatar_url \t login $user.login ")

                    /*
                     * check if the user is not saved yet in the database before
                     * doing the insert query
                    */
                    var userExiste: User? = null
                    userExiste = dao?.getUserByLogin(user.login.toString())

                    if( userExiste == null)
                        dao?.insertUser(user)

                    println( " \n !isEmpty()-dao2-user $user.id \t name $user.name \t avatar $user.avatar_url \t login $user.login ")
                }
            }
        }



        /*if( !userJson.isEmpty() ){

            // TODO: Utiliser Moshi afin de convertir le JSON en objet de la classe User
            println( " \n !isEmpty()-id $user.id \t name $user.name \t avatar $user.avatar_url \t login $user.login ")

            val moshi = Moshi.Builder()
                    .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
                    //.add(DefaultOnDataMismatchAdapter.newFactory(VideoType::class.java, VideoType.unknown)) /* 1 */
                    .add(DefaultOnDataMismatchAdapter.newFactory(MediaStore.Video::class.java, null)) /* 2 */
                    //.add(DefaultOnDataMismatchAdapter.newFactory(Content::class.java, null)) /* 3 */
                    //.add(FilterNullValuesFromListAdapter.newFactory(Content::class.java)) /* 4 */
                    .add(DefaultOnDataMismatchAdapter.newFactory(User::class.java, null)) /* 5 */
                    .add(KotlinJsonAdapterFactory())
                    .build()
            val adapter = moshi.adapter(User::class.java)
            user = adapter.fromJson(userJson)
            println(  " \n user => " + adapter.toJson(user)  )
        }*/

        return user
    }


     override fun onProgressUpdate(vararg values: User) {

        super.onProgressUpdate(*values)

        var user = values[0]

        val mainActivity: MainActivity = activityRef.get() ?: return

        mainActivity.displayUser(user, true)
    }



    /*
     * the result of the task did in background function is sent as
     * parameter to this function.
     * this function will send the result to the mainActivity
     */
    /*override fun onPostExecute(userStr: User?) {

        super.onPostExecute(userStr)
        val activity: MainActivity = activityRef.get() ?: return

        if (userStr == null)
            activity.displayErrorMessage()
        else
            activity.displayUser(userStr, false)

    }*/



    /*
     * Retreive a string from the gitHub. actually, that string represents the users.
     *
     * @return a string, a concatenation off all the web page data
     */
    private fun fetchUserJson(userName: String): String {

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
