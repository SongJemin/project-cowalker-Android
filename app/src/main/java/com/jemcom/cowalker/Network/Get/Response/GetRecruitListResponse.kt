package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetRecruitList

data class GetRecruitListResponse (
        var message : String,
        var result : ArrayList<GetRecruitList>
)