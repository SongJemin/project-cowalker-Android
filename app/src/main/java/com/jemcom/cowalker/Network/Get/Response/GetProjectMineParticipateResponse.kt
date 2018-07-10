package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetProjectMineParticipate

data class GetProjectMineParticipateResponse (
        var message : String,
        var result : ArrayList<GetProjectMineParticipate>
)