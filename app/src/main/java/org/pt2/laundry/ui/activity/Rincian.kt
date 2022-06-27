package org.pt2.laundry.ui.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.FirebaseFirestore
import org.pt2.laundry.MainActivity
import org.pt2.laundry.R
import org.pt2.laundry.databinding.ActivityRincianBinding
import kotlin.random.Random

class Rincian : AppCompatActivity() {

    // Notification
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    private lateinit var binding: ActivityRincianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRincianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = "Rincian"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val rnds = "LD-" + Random.nextInt(10, 1000000).toString()
        binding.nomorLaundryDisplay.text = rnds
        binding.nameDisplay.text = intent.getStringExtra("name")
        binding.alamatDisplay.text = intent.getStringExtra("alamat")
        binding.phoneDisplay.text = intent.getStringExtra("phone")
        binding.weightDisplay.text = intent.getStringExtra("weight")
        if (intent.getStringExtra("notes").toString().isNotEmpty()) {
            binding.notesDisplay.text = intent.getStringExtra("notes")
        } else {
            binding.notesDisplay.text = "-"
        }
        binding.jenisDisplay.text = intent.getStringExtra("jenis")
        binding.priceDisplay.text = intent.getStringExtra("price")

        createNotificationChannel()

        binding.bottomNavigatinView.setOnClickListener {
            saveData()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun saveData() {
        val kodeTransaction: String = binding.nomorLaundryDisplay.text as String
        val name: String = binding.nameDisplay.text as String
        val alamat: String = binding.alamatDisplay.text as String
        val phone: String = binding.phoneDisplay.text as String
        val weight: CharSequence? = binding.weightDisplay.text
        val notes: String = binding.notesDisplay.text as String
        val jenis: String = binding.jenisDisplay.text as String
        val status = "Proses"
        val totalPrice: CharSequence? = binding.priceDisplay.text

        val db = FirebaseFirestore.getInstance()
        val laundry: MutableMap<String, Any> = HashMap()
        laundry["kodeTransaksi"] = kodeTransaction
        laundry["name"] = name
        laundry["address"] = alamat
        laundry["phoneNumber"] = phone
        laundry["weight"] = weight.toString().toInt()
        laundry["notes"] = notes
        laundry["jenisKiloan"] = jenis
        laundry["status"] = status
        laundry["totalPrice"] = totalPrice.toString().toInt()

        db.collection("laundry").add(laundry)
            .addOnSuccessListener {
                Toast.makeText(this@Rincian, "Berhasil", Toast.LENGTH_SHORT).show()
                sendNotification()
            }
            .addOnFailureListener {
                Toast.makeText(this@Rincian, "Gagal", Toast.LENGTH_SHORT).show()
            }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notikasi Title"
            val descriptionText = "Deskripsi"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val bitmapLargeIcon =
            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.laundry)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.laundry)
            .setContentTitle("Transaksi baru!")
            .setContentText("Ada transaksi laundry baru nih! Ketuk untuk melihat")
            .setLargeIcon(bitmapLargeIcon)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }
}