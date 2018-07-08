package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetMessageLook

data class GetMessageLookResponse (
        var message:String,
        var result : ArrayList<GetMessageLook>
)