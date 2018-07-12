package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetIntroMine

data class GetIntroMineResponse (
        var message : String,
        var result : ArrayList<GetIntroMine>
)