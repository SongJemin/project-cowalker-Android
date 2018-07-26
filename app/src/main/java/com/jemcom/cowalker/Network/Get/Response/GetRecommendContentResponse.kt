package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetRecommendContentMessage

data class GetRecommendContentResponse (
        var message : String,
        var result : ArrayList<GetRecommendContentMessage>
)