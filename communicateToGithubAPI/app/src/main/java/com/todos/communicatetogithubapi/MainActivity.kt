package com.todos.communicatetogithubapi

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.View
import android.widget.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    //private var searchViewUser:SearchView?=null
    private lateinit var searchViewUser: SearchView
    private var layoutProfile: LinearLayout? = null
    //private var progressBarProfile:ProgressBar?=null
    private lateinit var progressBarProfile: ProgressBar
    //private val progressBarProfile:ProgressBar
    //private lateinit var progressBarProfile:ProgressBar

    private lateinit var tvUserName: TextView
    //private val tvUserName: TextView by bind(R.id.tv_user_name)
    //private val tvUserName by bind<TextView>(R.id.tv_user_name)


    /*
    val tvUserName = findViewById(R.id.tv_user_name) as TextView
    val searchViewUser = findViewById(R.id.search_view_user) as SearchView
    val layoutProfile = findViewById(R.id.layout_profile) as LinearLayout
    val progressBarProfile = findViewById(R.id.progress_bar_profile) as ProgressBar
    */


    private var tvEssai:TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //searchViewUser = findViewById(R.id.search_view_user) as SearchView
        searchViewUser = findViewById(R.id.search_view_user) as SearchView
        layoutProfile = findViewById(R.id.layout_profile) as LinearLayout
        //progressBarProfile = findViewById(R.id.progress_bar_profile) as ProgressBar
        progressBarProfile = bind(R.id.progress_bar_profile)
        tvEssai = findViewById(R.id.tv_essai) as TextView
        tvUserName = bind(R.id.tv_user_name)




        setUpSearchView()
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

        searchViewUser.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            // notify the UI when the word in textview change
            override fun onQueryTextChange(newText: String?): Boolean {
                tvEssai?.text =" \n try Search" + newText
                return false
            }

            // submit the word to search
            override fun onQueryTextSubmit(query: String?): Boolean {

                tvEssai?.text =" \n try Search" + query
                searchUser(query)
                return true
            }

        })

        searchViewUser.isSubmitButtonEnabled = true
    }



    /*
    private fun setUpSearchView1() {

        searchViewUser?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            // notify the UI when the word in textview change
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            // submit the word to search
            override fun onQueryTextSubmit(query: String?): Boolean {

                tvEssai.setText(" \n try Search" + query)
                searchUser(query)
                return true
            }

        })

        searchViewUser?.isSubmitButtonEnabled = true
    }
    */





    /*

     */
    private fun searchUser(userName: String?) {

        layoutProfile?.visibility = View.GONE
        progressBarProfile?.visibility = View.VISIBLE

        var fetchUserTask : FetchUserTask? = null

        if (fetchUserTask != null)
            fetchUserTask!!.cancel(true)

        fetchUserTask = FetchUserTask(this)
        fetchUserTask!!.execute(userName)

        tvEssai?.text = fetchUserTask!!.execute(userName).get()



        /*
        layoutProfile.visibility = View.GONE
        progressBarProfile.visibility = View.VISIBLE

        var  fetchUserTask : FetchUserTask = null

        if (fetchUserTask != null)
            fetchUserTask!!.cancel(true)

        fetchUserTask = FetchUserTask(this)
        fetchUserTask!!.execute(userName)
        */


    }






    fun displayErrorMessage() {
        progressBarProfile.visibility = View.GONE
        Toast.makeText(this, this.getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }



    fun displayUser(user: String, loading: Boolean) {
        layoutProfile?.visibility = View.VISIBLE
        tvUserName.text = user

        if (!loading)
            progressBarProfile.visibility = View.GONE
    }





}
