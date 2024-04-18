package com.driuft.random_pets_starter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import okhttp3.Headers
import values.PetAdapter
import kotlin.properties.Delegates
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var page: TextView
    private var pageLetter by Delegates.notNull<Char>()
    private lateinit var rvPets: RecyclerView
    private lateinit var rvNames: RecyclerView
    private lateinit var petList: MutableList<String>
    private lateinit var petNameList: MutableList<String>
    private lateinit var petIDList: MutableList<String>
    private lateinit var backButton: Button
    private lateinit var forwardButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        page = findViewById(R.id.textView)
        rvPets = findViewById(R.id.pet_list)
        rvNames = findViewById(R.id.pet_list)
        petNameList = mutableListOf()
        petIDList = mutableListOf()
        petList = mutableListOf()
        pageLetter = 'a'
        //backButton = findViewById(R.id.Back)
       forwardButton = findViewById(R.id.Next)



        getDogImageURL()

    }

    private fun getDogImageURL() {
        forwardButton.setOnClickListener {
            ++pageLetter
            page.text = pageLetter.toString()
            getDogImageURL()
        }
        val client = AsyncHttpClient()
        //val choice = Random.nextInt(1422)
        val characterJSON = "https://www.themealdb.com/api/json/v1/1/search.php?f=$pageLetter"


        client[characterJSON, object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog Success", "$json")
                var petImageArray = json.jsonObject.getJSONArray("meals")
                  //json.jsonObject.getJSONArray("images").getJSONObject(0).getString("href")
                for (i in 0 until petImageArray.length()) {
                   petList.add(petImageArray.getJSONObject(i).getString("strMealThumb"))

                }
                for (i in 0 until petImageArray.length()){
                    petNameList.add(petImageArray.getJSONObject(i).getString("strMeal"))

                }
                for (i in 0 until petImageArray.length()){
                    petIDList.add(petImageArray.getJSONObject(i).getString("strCategory"))
                }






                val adapter = PetAdapter(petList, petNameList, petIDList)
                rvNames.adapter = adapter
                rvPets.adapter = adapter
                rvPets.layoutManager = LinearLayoutManager(this@MainActivity)

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }
}