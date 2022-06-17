package org.pt2.laundry.riwayat

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.pt2.laundry.R
import org.pt2.laundry.model.Laundry
import org.pt2.laundry.ui.activity.DetailActivity
import java.text.NumberFormat
import java.util.*

class RiwayatAdapter(
    private val laundryList: ArrayList<Laundry>
) : RecyclerView.Adapter<RiwayatAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_riwayat,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = laundryList[position]

        holder.cardClick.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("kodeT", currentitem.kodeTransaksi.toString())
            it.context.startActivity(intent)
        }

        holder.kodeDisplay.text = currentitem.kodeTransaksi
        holder.nameDisplay.text = currentitem.name
        holder.jenisDisplay.text = currentitem.jenisKiloan
        holder.statusDisplay.text = currentitem.status
        val berat = currentitem.weight.toString() + " kg"
        holder.weightDisplay.text = berat

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        val result = formatter.format(currentitem.totalPrice.toString().toInt())

        holder.hargaDisplay.text = result

    }

    override fun getItemCount(): Int {

        return laundryList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val kodeDisplay: TextView = itemView.findViewById(R.id.nomorText)
        val nameDisplay: TextView = itemView.findViewById(R.id.nameDisplay)
        val jenisDisplay: TextView = itemView.findViewById(R.id.jenisDisplay)
        val statusDisplay: TextView = itemView.findViewById(R.id.notifText)
        val weightDisplay: TextView = itemView.findViewById(R.id.beratDisplay)
        val hargaDisplay: TextView = itemView.findViewById(R.id.totalDisplay)

        val cardClick: CardView = itemView.findViewById(R.id.cardBox)

    }
}