package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetAlarm

data class GetAlarmResponse (
        var message : String,
        var result : ArrayList<GetAlarm>
)