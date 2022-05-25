package org.pt2.laundry.model

data class Laundry(
    val id: String,
    val kodeTransaction : String,
    val name: String,
    val alamat: String,
    val phone: String,
    val weight: CharSequence?,
    val notes: String,
    val jenis: String,
    val status: String,
    val totalPrice: CharSequence?
)
