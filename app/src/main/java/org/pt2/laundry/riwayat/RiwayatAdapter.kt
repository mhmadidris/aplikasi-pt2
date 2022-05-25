package org.pt2.laundry.riwayat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.pt2.laundry.R
import org.pt2.laundry.model.Laundry
import org.pt2.laundry.model.Riwayat
import java.text.DecimalFormat

class RiwayatAdapter(val lCtx: Context, val layoutResId: Int, val ldList: MutableList<Riwayat>) :
    ArrayAdapter<Riwayat>(lCtx, layoutResId, ldList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lCtx)

        val view: View = layoutInflater.inflate(layoutResId, null)

        val kodeDisplay: TextView = view.findViewById(R.id.nomorText)
        val nameDisplay: TextView = view.findViewById(R.id.nameDisplay)
        val beratDisplay: TextView = view.findViewById(R.id.beratDisplay)
        val jenisDisplay: TextView = view.findViewById(R.id.jenisDisplay)
        val totalDisplay: TextView = view.findViewById(R.id.totalDisplay)

        val laundry = ldList[position]

        kodeDisplay.text = laundry.kodeTransaction
        nameDisplay.text = laundry.name
        val berat = laundry.weight
        beratDisplay.text = berat + " kg"
        jenisDisplay.text = laundry.jenis
        val rupiah = laundry.totalPrice
        totalDisplay.text = "Rp." + rupiah

        return view
    }
}