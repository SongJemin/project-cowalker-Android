package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetProjectMemberMessage


data class GetProjectMemberResponse (
        var message : String,
        var member : ArrayList<GetProjectMemberMessage>
)