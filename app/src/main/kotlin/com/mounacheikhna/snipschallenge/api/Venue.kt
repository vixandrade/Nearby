package com.mounacheikhna.snipschallenge.api

data class Venue
(
    val id: String,
    val name: String,
    var contact: Contact?,
    val location: VenueLocation,
    val canonicalUrl: String?,
    val hours: Hours?,
    val verified: Boolean,
    val rating: Double?,
    val description: String
)

data class Contact(
    val phone: String,
    val formattedPhone: String
)

data class Hours(
    val status: String,
    val isOpen: Boolean
)

data class VenueLocation
(
    val lat: Double? = null,
    val lng: Double? = null,
    val distance: Int? = null,
    val address: String? = null,
    val crossStreet: String? = null,
    val city: String? = null,
    val state: String? = null,
    val postalCode: String? = null,
    val country: String? = null,
    var formattedAddress: Array<String>?
)

data class Category
(
    val id: String,
    val name: String,
    val pluralName: String,
    val icon: String
)

data class Stats
(
    val checkinsCount: Int,
    val usersCount: Int,
    val tipCount: Int
)