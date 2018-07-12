package com.jemcom.cowalker.Network.Get

data class GetProjectDetailMessage (
        var title : String,
        var summary : String,
        var area : String,
        var department : String,
        var aim : String,
        var explain : String,
        var create_at : String,
        var img_url : ArrayList<String>,
        var project_user_name : String,
        var project_user_profile_url : String?,
        var user_idx : String
)