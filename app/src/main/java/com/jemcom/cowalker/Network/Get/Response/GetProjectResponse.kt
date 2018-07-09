package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetProjectMessage

data class GetProjectResponse (
        var message : String,
        var result : ArrayList<GetProjectMessage>
)