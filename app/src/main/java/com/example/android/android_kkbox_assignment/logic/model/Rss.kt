package com.example.android.android_kkbox_assignment.logic.model

import android.os.Parcelable
import com.tickaroo.tikxml.TypeConverter
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Xml
data class Rss(
    @Element
    val channel: Channel?
) : Parcelable


@Parcelize
@Xml
data class Channel(
    @PropertyElement
    val title: String?,
    @Element
    val image: Image?,
    @Element (name = "item")
    val episodes: List<Episode>?
) : Parcelable

@Parcelize
@Xml
data class Image(
    @PropertyElement
    val url: String?
) : Parcelable

@Parcelize
@Xml
data class Episode(
    @PropertyElement
    val title: String?,
    @PropertyElement
    val description: String?,
    @PropertyElement
    val pubDate: String?,
    @PropertyElement(name = "itunes:summary")
    val summary: String?,
    @Element
    val sound: Sound?,
    @Element
    val image: EpisodeImage?
) : Parcelable

@Parcelize
@Xml (name = "enclosure")
data class Sound(
    @Attribute
    val type: String?,
    @Attribute
    val url: String?
) : Parcelable

@Parcelize
@Xml (name = "itunes:image")
data class EpisodeImage(
    @Attribute (name = "href")
    val url: String?
) : Parcelable

