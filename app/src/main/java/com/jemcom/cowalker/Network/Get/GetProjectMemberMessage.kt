package com.jemcom.cowalker.Network.Get

data class GetProjectMemberMessage (
        var member_idx : Int,
        var position : String,
        var profile_url : String
)