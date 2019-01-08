package com.todos.communicatetogithubapi

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

//import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
@Entity(tableName = "users_github")
data class User(
        @Json(name = "login") var login: String? = "",
        @Json(name = "id") @PrimaryKey var id: Int? = 0,
        @ColumnInfo(name = "avatar_url") @Json(name = "avatar_url") var avatarUrl: String? = "",
        @ColumnInfo(name = "gravatar_id") @Json(name = "gravatar_id") var gravatarId: String? = "",
        @Json(name = "url") var url: String? = "",
        @ColumnInfo(name = "html_url") @Json(name = "html_url") var htmlUrl: String? = "",
        @ColumnInfo(name = "followers_url") @Json(name = "followers_url") var followersUrl: String? = "",
        @ColumnInfo(name = "following_url") @Json(name = "following_url") var followingUrl: String? = "",
        @ColumnInfo(name = "gists_url") @Json(name = "gists_url") var gistsUrl: String? = "",
        @ColumnInfo(name = "starred_url") @Json(name = "starred_url") var starredUrl: String? = "",
        @ColumnInfo(name = "subscriptions_url") @Json(name = "subscriptions_url") var subscriptionsUrl: String? = "",
        @ColumnInfo(name = "organizations_url") @Json(name = "organizations_url") var organizationsUrl: String? = "",
        @ColumnInfo(name = "repos_url") @Json(name = "repos_url") var reposUrl: String? = "",
        @ColumnInfo(name = "events_url") @Json(name = "events_url") var eventsUrl: String? = "",
        @ColumnInfo(name = "received_events_url") @Json(name = "received_events_url") var receivedEventsUrl: String? = "",
        @Json(name = "type") var type: String? = "",
        @ColumnInfo(name = "site_admin") @Json(name = "site_admin") var siteAdmin: Boolean? = false,
        @Json(name = "name") var name: String? = "",
        @Json(name = "company") var company: String? = "",
        @Json(name = "blog") var blog: String? = "",
        @Json(name = "location") var location: String? = "",
        @Json(name = "email") var email: String? = "",
        @Json(name = "hireable") var hireable: Boolean? = false,
        @Json(name = "bio") var bio: String? = "",
        @ColumnInfo(name = "public_repos") @Json(name = "public_repos") var publicRepos: Int? = 0,
        @ColumnInfo(name = "public_gists") @Json(name = "public_gists") var publicGists: Int? = 0,
        @Json(name = "followers") var followers: Int? = 0,
        @Json(name = "following") var following: Int? = 0,
        @ColumnInfo(name = "created_at") @Json(name = "created_at") var createdAt: String? = "",
        @ColumnInfo(name = "updated_at") @Json(name = "updated_at") var updatedAt: String? = ""
)/*{
    //@Ignore
    //constructor():this(id=0)
    constructor():this("",0,null,"",null,"",
            "","","", "", "", "",
            "", "", "", "", false,
            "", "", "", "", "", false,
            "", 0, 0, 0, 0,
            "", "")
}*/
annotation class Json(val name: String)
