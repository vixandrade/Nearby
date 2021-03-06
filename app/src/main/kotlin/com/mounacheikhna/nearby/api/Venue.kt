package com.mounacheikhna.nearby.api

import android.os.Parcel
import android.os.Parcelable


public class Venue: Parcelable { //Venue needs to be parcelable to be passed (with VenueResult) in intent to VenueActivity

    val id: String
    val name: String
    val contact: Contact?
    val location: VenueLocation
    val canonicalUrl: String?
    val hours: Hours?
    val verified: Boolean
    val rating: Double?
    val description: String?
    val price: Price?

    constructor(id: String, name: String, contact: Contact?, location: VenueLocation,
                canonicalUrl: String?, hours: Hours?, verified: Boolean, rating: Double?,
                description: String?, price: Price?) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.canonicalUrl = canonicalUrl;
        this.hours = hours;
        this.verified = verified;
        this.rating = rating;
        this.description = description;
        this.price = price;
    }

    constructor(source: Parcel) {
        id = source.readString()
        name = source.readString()
        contact = source.readTypedObject(Contact.CREATOR)
        location = source.readTypedObject(VenueLocation.CREATOR)
        canonicalUrl = source.readString()
        hours = source.readTypedObject<Hours>(Hours.CREATOR)
        verified = source.readInt() == 1
        rating = source.readDouble()
        description = source.readString()
        price = source.readTypedObject<Price>(Price.CREATOR)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(name)
        dest?.writeTypedObject(contact, flags)
        dest?.writeTypedObject(location, flags)
        dest?.writeString(canonicalUrl ?: "")
        dest?.writeTypedObject(hours, flags)
        dest?.writeInt(if (verified) 1 else 0)
        dest?.writeDouble(rating ?: 0.0)
        dest?.writeString(description ?: "")
        dest?.writeTypedObject(price, flags)
    }

    override fun describeContents() = 0

    companion object {
        public val CREATOR: Parcelable.Creator<Venue> = object : Parcelable.Creator<Venue> {
            override fun createFromParcel(source: Parcel): Venue {
                return Venue(source)
            }

            override fun newArray(size: Int): Array<Venue?> {
                return arrayOfNulls(size)
            }
        }
    }

}

data class Contact(
    val phone: String?,
    val formattedPhone: String?
) : Parcelable {
    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(phone ?: "")
        dest?.writeString(formattedPhone ?: "")
    }

    companion object {
        public val CREATOR: Parcelable.Creator<Contact> = object : Parcelable.Creator<Contact> {
            override fun createFromParcel(source: Parcel): Contact {
                return Contact(source)
            }

            override fun newArray(size: Int): Array<Contact?> {
                return arrayOfNulls(size)
            }
        }
    }

}

data class Hours(
    val status: String?,
    val isOpen: Boolean
) : Parcelable {
    constructor(source: Parcel) : this(source.readString(), source.readInt() == 1)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(status ?: "")
        dest?.writeInt(if (isOpen) 1 else 0)
    }

    companion object {
        public val CREATOR: Parcelable.Creator<Hours> = object : Parcelable.Creator<Hours> {
            override fun createFromParcel(source: Parcel): Hours {
                return Hours(source)
            }

            override fun newArray(size: Int): Array<Hours?> {
                return arrayOfNulls(size)
            }
        }
    }
}

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
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readDouble(), source.readDouble(),
        source.readInt(), source.readString(), source.readString(), source.readString(),
        source.readString(), source.readString(), source.readString(),
        arrayOf())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeDouble(lat ?: 0.0)
        dest?.writeDouble(lng ?: 0.0)
        dest?.writeInt(distance ?: 0)
        dest?.writeString(address ?: "")
        dest?.writeString(crossStreet ?: "")
        dest?.writeString(city ?: "")
        dest?.writeString(state ?: "")
        dest?.writeString(postalCode ?: "")
        dest?.writeString(country ?: "")
        dest?.writeStringArray(formattedAddress ?: arrayOf())
    }

    companion object {
        public val CREATOR: Parcelable.Creator<VenueLocation> = object : Parcelable.Creator<VenueLocation> {
            override fun createFromParcel(source: Parcel): VenueLocation {
                return VenueLocation(source)
            }

            override fun newArray(size: Int): Array<VenueLocation?> {
                return arrayOfNulls(size)
            }
        }
    }
}

data class Price(
    val tier: String?,
    val message: String?
) : Parcelable {
    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(tier ?: "")
        dest?.writeString(message ?: "")
    }

    companion object {
        public val CREATOR: Parcelable.Creator<Price> = object : Parcelable.Creator<Price> {
            override fun createFromParcel(source: Parcel): Price {
                return Price(source)
            }

            override fun newArray(size: Int): Array<Price?> {
                return arrayOfNulls(size)
            }
        }
    }

}