package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetProjectMineApply

data class GetProjectMineApplyResponse (
        var message : String,
        var result : ArrayList<GetProjectMineApply>
)