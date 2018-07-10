package com.jemcom.cowalker.Network.Get.Response

import com.jemcom.cowalker.Network.Get.GetIntroOther

data class GetIntroOtherResponse(
        var message : String, // 응답 바디에서 "message" : "success" -> "message"는 이름 그대로 쓰기 + "success"는 스트링이니까 String!
        var result : ArrayList<GetIntroOther> // "result"는 말 그대로, 그 뒤에는 ArrayList다. {} 는 Class, []는 배열이다. 배열 안에 또 Class가 있으니까 GetIntroOther를 만들어 준다. Class로.
)