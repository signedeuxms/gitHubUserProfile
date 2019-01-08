package com.todos.communicatetogithubapi

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.IdRes
import android.util.Log
import android.view.View
import android.widget.*
//import com.todos.communicatetogithubapi.R.id.search_view_user
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {

    private var state = Bundle()
    private var state1: Bundle? = null
    private var image1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpSearchView()

        // receive the bundle
        if (savedInstanceState != null) {
            // TODO: restaurer l'état
            //state.putAll(savedInstanceState.getBundle("newState"))
            tv_fullname.text = savedInstanceState.getString("login1")
            tv_user_name.text = savedInstanceState.getString("name1")
            //iv_avatar.setImageBitmap()
        }

    }


    // store the bundle
    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {

        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putString("image1", image1)
        outState?.putString("login1", tv_fullname.text.toString())
        outState?.putString("name1", tv_user_name.text.toString())
        //tv_user_name.text = user.login
        //tv_fullname.text = user.name
        //outState?.putAll(outPersistentState)
    }


    private fun restoreState( state: Bundle){
        state.putAll(state.getBundle("newState"))
    }


    fun <T : View> Activity.bind(@IdRes res: Int): T {
        @Suppress("UNCHECKED CAST")
        return findViewById(res) as T
    }


    fun <T : View> View.bind(@IdRes res: Int): T {
        @Suppress("UNCHECKED CAST")
        return findViewById(res) as T
    }


    /**
     * SPECIFICATION
     * La procédure commence par assigner un écouteur à la barre de recherche.
     * Il est créé à l’aide du mot-clé « object ». Deux méthodes doivent être
     * implémentées. L’implémentation de la méthode onQueryTextSubmit contient un
     * appel vers une méthode permettant de rechercher l’utilisateur.
     * Finalement, la procédure setUpSearchView active le bouton de
     * soumission. Appelez cette procédure dans la méthode onCreate de MainActivity
     * @return true, car la requête a été traitée. La méthode onQueryTextChange
     * ne fait que retourne false parce que nous n’avons pas besoin de traiter ce type
     * de requête
     */
    private fun setUpSearchView() {

        search_view_user.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            // notify the UI when the word in textview change
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            // submit the word to search
            override fun onQueryTextSubmit(query: String?): Boolean {

                println(" \n query $query")
                searchUser(query)
                return true
            }
        })

        search_view_user.isSubmitButtonEnabled = true
    }



    /*

     */
    private fun searchUser(userName: String?) {

        layout_profile.visibility = View.GONE
        progress_bar_profile.visibility = View.VISIBLE

        var fetchUserTask : FetchUserTask? = null

        if (fetchUserTask != null)
            fetchUserTask!!.cancel(true)


        println(" \n fetchUserTask ")
        fetchUserTask = FetchUserTask(this)
        var user: User? = fetchUserTask!!.execute(userName).get()

        println( " \n main-id $user.id \t name $user.name \t avatar $user.avatar_url \t login $user.login ")

    }



    fun displayErrorMessage() {
        progress_bar_profile.visibility = View.GONE
        Toast.makeText(this, this.getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }



    fun displayUser(user: User, loading: Boolean) {

        println( " \n displayUser-id $user.id \t name $user.name \t avatar $user.avatar_url \t login $user.login ")
        image1 = user.avatarUrl
        layout_profile.visibility = View.VISIBLE
        tv_user_name.text = user.login
        tv_fullname.text = user.name

        // TODO: Charger l'image dans iv_avatar
        iv_avatar.loadAvatar(user.avatarUrl.toString())
        //iv_avatar.loadAvatar(user.avatarUrl.toString(), "huh" )

        //bitmapAvatar(user.avatarUrl.toString() )
        /*var bmp: Bitmap? = null
        try{
            println( " \n Bitmap-1 \t $user.avatarUrl ")
            bmp = LoadImageTask(this).execute(user.avatarUrl ).get()
        }catch (exp: Exception){
            println( " \n Bitmap-2")
            Log.e("error-bitmap => ", exp.message)
            exp.printStackTrace()
        }finally{
            println( " \n Bitmap-3")
            iv_avatar?.setImageBitmap(bmp)
        }*/

        if (!loading)
            progress_bar_profile.visibility = View.GONE
    }


    private fun bitmapAvatar(urlToImage: String){
        var bmp: Bitmap? = null
        try{
            println( " \n Bitmap-1 \t urlToImage ")
            bmp = LoadImageTask(this).execute(urlToImage ).get()
        }catch (exp: Exception){
            println( " \n Bitmap-2")
            Log.e("error-bitmap => ", exp.message)
            exp.printStackTrace()
        }finally{
            println( " \n Bitmap-3")
            iv_avatar?.setImageBitmap(bmp)
        }
    }



}


/** REFERENCES
 * https://www.mytrendin.com/parsing-json-moshi-library-android-using-kotiln/
 * https://medium.com/@agrawalsuneet/safe-calls-vs-null-checks-in-kotlin-f7c56623ab30
 * https://kotlinlang.org/docs/reference/data-classes.html
 * singleton connexion to database
 * https://medium.com/mindorks/android-architecture-components-room-and-kotlin-f7b725c8d1d
 */
