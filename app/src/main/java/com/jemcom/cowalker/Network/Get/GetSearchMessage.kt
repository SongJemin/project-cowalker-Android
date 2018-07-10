package com.jemcom.cowalker.Network.Get

data class GetSearchMessage (
        var project_idx : String?,
        var title : String?,
        var summary : String?,
        var area : String?,
        var department : String?,
        var aim : String?,
        var explain : String?,
        var user_idx : Int?,
        var create_at : String?,
        var img_url : String?
)