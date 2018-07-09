package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetRecruitDetail

data class GetRecruitDetailResponse (
        var message : String,
        var result : ArrayList<GetRecruitDetail>,
        var btnResult : String
)