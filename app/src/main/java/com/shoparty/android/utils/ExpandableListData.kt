package com.shoparty.android.utils

import java.util.*
internal object ExpandableListData {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail =
                HashMap<String, List<String>>()
            val Ipsum1: MutableList<String> =
                ArrayList()
            Ipsum1.add("Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the")
           /* myFavCricketPlayers.add("Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the")
            myFavCricketPlayers.add("Shane Watson")
            myFavCricketPlayers.add("Ricky Ponting")
            myFavCricketPlayers.add("Shahid Afridi")
         */   val Ipsum2: MutableList<String> = ArrayList()
            Ipsum2.add("Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the")

            val Ipsum3: MutableList<String> = ArrayList()
            Ipsum3.add("Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the")


            expandableListDetail["Lorem Ipsum is simply dummy text of the "] = Ipsum1
            expandableListDetail["Lorem Ipsum is simply dummy text of the"] = Ipsum2
            expandableListDetail["Lorem Ipsum is simply  dummy text of the"] = Ipsum3
            return expandableListDetail
        }
}