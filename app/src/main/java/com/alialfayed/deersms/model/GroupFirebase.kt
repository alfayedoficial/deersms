package com.alialfayed.deersms.model

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class GroupFirebase {
    private lateinit var groupId :String
    private lateinit var groupName :String
    private lateinit var groupNumbers :Array<List<GroupNambers>>
    private lateinit var userID: String

    constructor(groupId :String ,groupName :String , groupNumbers :Array<List<GroupNambers>> , userID: String )
}
class GroupNambers{
    private lateinit var name:String
    private lateinit var phone:String
}