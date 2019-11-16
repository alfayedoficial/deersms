package com.alialfayed.deersms.model

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class GroupFirebase {
    private lateinit var groupId :String
    private lateinit var groupName :String
    private lateinit var groupNumbers :ArrayList<ContactList>
    private lateinit var userID: String

    constructor(userid: String, groupName: String, groupMembers: ArrayList<ContactList>) {
        this.groupId = userid
        this.groupName = groupName
        this.groupNumbers = groupMembers
    }

    constructor() {}

    constructor( userID: String ,groupId :String ,groupName :String , groupNumbers :ArrayList<ContactList>){
        this.userID =userID
        this.groupId = groupId
        this.groupName = groupName
        this.groupNumbers = groupNumbers
    }

    fun getUserID(): String{ return userID }
    fun getGroupId(): String{ return groupId }
    fun getGroupName(): String{ return groupName }
    fun getGroupNumbers(): ArrayList<ContactList>{ return groupNumbers }
}
