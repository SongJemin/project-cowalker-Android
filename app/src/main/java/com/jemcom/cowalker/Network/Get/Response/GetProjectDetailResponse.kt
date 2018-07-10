package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetProjectDetailMessage
import com.jemcom.cowalker.Network.Get.GetProjectMessage

data class GetProjectDetailResponse (
        var message : String,
        var result : ArrayList<GetProjectDetailMessage>,
        var user : String
)