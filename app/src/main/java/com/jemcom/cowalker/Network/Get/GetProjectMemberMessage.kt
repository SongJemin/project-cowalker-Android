package com.jemcom.cowalker.Network.Get

data class GetProjectMemberMessage (
        var member_idx : String,
        var position : String,
        var profile_url : String
)