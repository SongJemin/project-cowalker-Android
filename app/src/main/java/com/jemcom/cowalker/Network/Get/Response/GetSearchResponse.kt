package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetSearchMessage

data class GetSearchResponse (
        var message : String,
        var result : ArrayList<GetSearchMessage>
)