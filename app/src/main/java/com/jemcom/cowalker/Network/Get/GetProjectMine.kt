package com.jemcom.cowalker.Network.Get

data class GetProjectMine (
        var title : String,
        var summary : String,
        var area : String,
        var department : String,
        var aim : String,
        var explain : String,
        var create_at : String,
        var img_url : ArrayList<String>
)