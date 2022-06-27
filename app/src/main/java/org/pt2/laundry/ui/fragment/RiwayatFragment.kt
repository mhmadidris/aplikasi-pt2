package org.pt2.laundry.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.firestore.FirebaseFirestore
import org.pt2.laundry.R
import org.pt2.laundry.databinding.FragmentRiwayatBinding
import org.pt2.laundry.model.Laundry
import org.pt2.laundry.riwayat.RiwayatAdapter

class RiwayatFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var laundryRecylerView: RecyclerView
    private lateinit var laundryArrayList: ArrayList<Laundry>
    private lateinit var binding: FragmentRiwayatBinding

    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onCreateActivity()
        binding = FragmentRiwayatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shimmerFrameLayout = binding.shimmerRiwayat
        shimmerFrameLayout.startShimmer()

        laundryRecylerView = binding.listView
        laundryRecylerView.layoutManager = LinearLayoutManager(this.context)
        laundryRecylerView.setHasFixedSize(true)

        laundryArrayList = arrayListOf()
        getUserData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    private fun getUserData() {

        db = FirebaseFirestore.getInstance()
        db.collection("laundry").get().addOnSuccessListener { result ->
            shimmerFrameLayout.stopShimmer()
            if (result != null) {
                shimmerFrameLayout.visibility = View.GONE
                binding.listView.visibility = View.VISIBLE
                for (userSnapshot in result) {

                    val user = userSnapshot.toObject(Laundry::class.java)
                    laundryArrayList.add(user)

                }

                laundryRecylerView.adapter = RiwayatAdapter(laundryArrayList)
            } else {
                shimmerFrameLayout.visibility = View.GONE
                binding.emptyDisplay.visibility = View.VISIBLE
            }
        }
    }

    private fun onCreateActivity() {
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}