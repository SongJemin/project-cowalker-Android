package com.jemcom.cowalker.Network.Get

data class GetApplyMemberMessage (
        var applicant : String,
        var profile_url : String,
        var user_name : String,
        var position : String,
        var apply_idx : Int
)