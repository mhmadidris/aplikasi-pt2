package org.pt2.laundry.model

data class Riwayat(
    val id: String,
    val kodeTransaction: String,
    val name: String,
    val weight: String,
    val jenis: String,
    val totalPrice: String
) {
    constructor() : this("", "", "", "", "", "") {
    }
}
