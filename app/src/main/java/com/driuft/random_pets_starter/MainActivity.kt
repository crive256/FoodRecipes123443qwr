package com.driuft.random_pets_starter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import okhttp3.Headers
import values.PetAdapter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var rvPets: RecyclerView
    private lateinit var rvNames: RecyclerView
    private lateinit var petList: MutableList<String>
    private lateinit var petNameList: MutableList<String>
    private lateinit var petIDList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPets = findViewById(R.id.pet_list)
        rvNames = findViewById(R.id.pet_list)
        petNameList = mutableListOf()
        petIDList = mutableListOf()
        petList = mutableListOf()

        getDogImageURL()
    }

    private fun getDogImageURL() {
        val client = AsyncHttpClient()
        //val choice = Random.nextInt(1422)
        val characterJSON = "https://www.digi-api.com/api/v1/digimon?pageSize=1422"


        client[characterJSON, object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog Success", "$json")
                var petImageArray = json.jsonObject.getJSONArray("content")
                  //json.jsonObject.getJSONArray("images").getJSONObject(0).getString("href")
                for (i in 0 until 1422) {
                   petList.add(petImageArray.getJSONObject(i).getString("image"))

                }
                for (i in 0 until 1422){
                    petNameList.add(petImageArray.getJSONObject(i).getString("name"))

                }
                for (i in 0 until 1422){
                    petIDList.add(petImageArray.getJSONObject(i).getString("id"))
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