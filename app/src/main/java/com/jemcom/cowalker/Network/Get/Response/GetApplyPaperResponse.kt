package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetApplyPaperMessage


data class GetApplyPaperResponse (
        var message : String,
        var result : ArrayList<GetApplyPaperMessage>
)