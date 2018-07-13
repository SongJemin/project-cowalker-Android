package com.jemcom.cowalker.Network.Put.Response

data class PutApplyModify (
        var position : String,
        var start_date : String,
        var end_date : String,
        var num : String,
        var task : String,
        var activity : String,
        var reward : String,
        var area : String,
        var ability : String,
        var career : String,
        var preference : String,
        var comment : String,
        var question_list : ArrayList<String>
)