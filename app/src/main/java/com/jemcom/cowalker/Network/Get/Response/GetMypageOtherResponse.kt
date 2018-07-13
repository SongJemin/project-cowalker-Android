package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetMypage
import com.jemcom.cowalker.Network.Get.GetOtherpage

data class GetMypageOtherResponse (
      var message : String,
      var data : ArrayList<GetOtherpage>,
      var user_status : String
)