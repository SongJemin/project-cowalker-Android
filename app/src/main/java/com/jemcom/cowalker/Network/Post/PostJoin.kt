package com.jemcom.cowalker.Network.Post

data class PostJoin (
        var introduce : String,
        var portfolio_url : String,
        var recruit_idx : String,
        var project_idx : String,
        var position : String,
        var answers : ArrayList<String>,
        var phone : String
)