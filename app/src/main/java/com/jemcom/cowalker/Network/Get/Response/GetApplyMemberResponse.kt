package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetApplyMemberMessage

data class GetApplyMemberResponse (
        var message : String,
        var result : ArrayList<GetApplyMemberMessage>
)