package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetMypage

data class GetMypageOtherResponse (
      var message : String,
      var data : ArrayList<GetMypage>,
      var user_status : String
)