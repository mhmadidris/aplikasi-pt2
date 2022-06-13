package org.pt2.laundry.model

data class Laundry(
    var kodeTransaksi : String ?= null,
    var name: String ?= null,
    val address: String?= null,
    val phoneNumber: String ?= null,
    var weight: Int ?= 0,
    val notes: String ?= null,
    var jenisKiloan: String ?= null,
    val status: String ?= null,
    var totalPrice: Int ?= 0
)