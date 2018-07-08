package com.jemcom.cowalker.Network.Get

data class GetMessage (
        var partner_idx : Int,
        var partner_name : String,
        var partner_profile_url : String,
        var contents : String,
        var create_at : String
)