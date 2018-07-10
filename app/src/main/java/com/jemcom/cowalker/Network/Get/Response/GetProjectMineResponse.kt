package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetProjectMine

data class GetProjectMineResponse (
        var message : String,
        var result : ArrayList<GetProjectMine>
)