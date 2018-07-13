package com.jemcom.cowalker.Network.Post

data class PostLogin (
        var email : String,
        var password : String,
        var fcm_token : String?
)