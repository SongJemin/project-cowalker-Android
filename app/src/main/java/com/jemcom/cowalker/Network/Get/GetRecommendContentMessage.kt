package com.jemcom.cowalker.Network.Get

data class GetRecommendContentMessage (
        var recommend_idx : Int,
        var recommender_idx : Int,
        var reason : String,
        var project_idx : String,
        var recruit_idx : String

)