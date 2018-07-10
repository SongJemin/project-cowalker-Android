package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetMypage

data class GetMypageResponse (
        var message : String,
        var data : ArrayList<GetMypage>

)