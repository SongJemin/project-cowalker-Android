package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetMessage

data class GetMessageResponse (
        var message : String,
        var result : ArrayList<GetMessage>
)