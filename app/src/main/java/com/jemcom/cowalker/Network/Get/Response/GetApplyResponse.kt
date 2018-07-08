package com.jemcom.cowalker.Network.Get.Response

data class GetApplyResponse (
        var apply_idx : String,
        var introduce : String,
        var portfolio_url : String,
        var phone : String,
        var recruit_idx : String,
        var applicant_idx : String,
        var recruit_at : String,
        var answers : ArrayList<String>
)