package com.jemcom.cowalker.Network.Get

data class GetApplyMemberMessage (
        var applicant_idx : Int,
        var profile_url : String,
        var user_name : String,
        var position : String,
        var apply_idx : String
)