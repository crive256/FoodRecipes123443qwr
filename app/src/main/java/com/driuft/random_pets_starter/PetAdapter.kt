package values

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.driuft.random_pets_starter.R
import org.w3c.dom.Text

class PetAdapter(private val petList: List<String>, private val petNameList: List<String>, private val petIDList: List<String>) : RecyclerView.Adapter<PetAdapter.ViewHolder>(){



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView
        val petName: TextView
        val petID: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            petImage = view.findViewById(R.id.pet_image)
            petName = view.findViewById(R.id.digimonName)
            petID = view.findViewById(R.id.AntiBody)
        }

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_image, parent, false)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(petList[position])
            .centerCrop()
            .into(holder.petImage)

        holder.petName.text = petNameList[position]
        holder.petID.text = petIDList[position]


    }
}

