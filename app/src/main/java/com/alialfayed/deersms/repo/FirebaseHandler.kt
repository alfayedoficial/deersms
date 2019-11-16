package com.alialfayed.deersms.repo

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.alialfayed.deersms.model.MessageFirebase
import com.alialfayed.deersms.viewmodel.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class FirebaseHandler(activity: Activity) {

    var mAuth: FirebaseAuth
    //    lateinit var mGoogleSignInClient: GoogleSignInClient
    var signInViewModel: SignInViewModel? = null
    var signUpViewModel: SignUpViewModel? = null
    var forgetPasswodViewModel: ForgetPasswodViewModel? = null
    var homeViewModel: HomeViewModel? = null
    var addGroupViewModel: AddGroupViewModel? = null
    var currentUser: FirebaseUser? = null
    lateinit var profileViewModel: ProfileViewModel


    lateinit var databaseReference: DatabaseReference
    lateinit var scheduleMessageViewModel: ScheduleMessageViewModel
    lateinit var addMessageViewModel: AddMessageViewModel
    internal lateinit var updateList: ArrayList<MessageFirebase>


    var activity: Activity? = null

    init {
        this.activity = activity
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }

    constructor(activity: Activity, profileViewModel: ProfileViewModel)
            : this(activity) {
        this.profileViewModel = profileViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }

    constructor(activity: Activity, signInViewModel: SignInViewModel)
            : this(activity) {
        this.signInViewModel = signInViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }

    constructor(activity: Activity, signUpViewModel: SignUpViewModel)
            : this(activity) {
        this.signUpViewModel = signUpViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }

    constructor(activity: Activity, forgetPasswodViewModel: ForgetPasswodViewModel)
            : this(activity) {
        this.forgetPasswodViewModel = forgetPasswodViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }

    constructor(activity: Activity, homeViewModel: HomeViewModel)
            : this(activity) {
        this.homeViewModel = homeViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }

    constructor(activity: Activity, addMessageViewModel: AddMessageViewModel)
            : this(activity) {
        this.addMessageViewModel = addMessageViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Message")
    }

    constructor(activity: Activity, addGroupViewModel: AddGroupViewModel)
            : this(activity) {
        this.addGroupViewModel = addGroupViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Groups")
    }

    constructor(activity: Activity, scheduleMessageViewModel: ScheduleMessageViewModel)
            : this( activity ) {
        this.scheduleMessageViewModel = scheduleMessageViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Message")
    }


    fun getFirebaseUser(): FirebaseUser { return mAuth.currentUser!! }

    fun signUp(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("signUp", "Success " + email)
                    mAuth.currentUser!!.sendEmailVerification()
                    signUpViewModel?.SignUpSuccessful()
                    Toast.makeText(activity, "Sign Up Success , please Sign In", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Log.i("signUp", "Failed")
                    signUpViewModel?.SignUpfailed()
                }
            })
    }

    fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(OnCompleteListener {
            if (it.isSuccessful) {
                if (mAuth.currentUser!!.isEmailVerified) {
                    Log.i("signIn", "Success" + email)
                    Toast.makeText(activity, " Success " + email, Toast.LENGTH_LONG).show()
                    signInViewModel?.SignInSuccessful()
                } else {
                    signInViewModel?.setMsgAlert("Please, verify your email address to login.")
                }

            } else {
                Log.i("signIn", "Fail")
                Toast.makeText(activity, " Failed " + email, Toast.LENGTH_LONG).show()
                signInViewModel?.SignInfailed()
            }
        })
    }

    fun resetPassword(email: String) {
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("forgetPassword", "Success" + email)
                    Toast.makeText(activity, "Please Check your Email", Toast.LENGTH_LONG).show()
                    forgetPasswodViewModel?.ForgetPasswordSuccessful()

                } else {
                    Log.i("forgetPassword", "Fail")
//                Toast.makeText(activity, " Failed " + email, Toast.LENGTH_LONG).show()
                    forgetPasswodViewModel?.ForgetPasswordfailed()
                }
            })
    }

    fun logout() { mAuth.signOut() }

    fun changePassword(oldPassword: String, password: String) {
        val user = getFirebaseUser()
        val credential = EmailAuthProvider
            .getCredential(user.email!!, oldPassword)
        user.reauthenticate(credential)
            .addOnCompleteListener(OnCompleteListener<Void> { task ->
                if (task.isSuccessful) {
                    user.updatePassword(password)
                        .addOnCompleteListener(OnCompleteListener<Void> { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity, " Password update ", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    " Sorry your have Error ",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                } else {
                }
            })
    }

//    fun addGroup(groupName: String, groupNumbers: Array<List<GroupNambers>>) {
//
//        /**
//         * create  Message Firebase
//         */
//        val groupId: String = databaseReference?.push()?.key.toString()
//        val groupFirebase = GroupFirebase(
//            groupId,
//            groupName,
//            groupNumbers,
//            FirebaseAuth.getInstance().currentUser!!.uid
//        )
//
//        /**
//         * insert Message Firebase
//         */
//        databaseReference?.child(groupId)?.setValue(groupFirebase)
//
//
//    }

    fun scheduleMessageRepository(
        personName: String, receiverNumber: String, SMSMessage: String,
        date: String, time: String, status: String, type: String,
        calendar: Long, smsDelivered: String
    ) {

        /**
         * create  Message Firebase
         */
        databaseReference = FirebaseDatabase.getInstance().getReference("Message")
        val smsId: String = databaseReference.push().key.toString()
        val message = MessageFirebase(
            smsId, personName, receiverNumber, SMSMessage, date, time, status, type,
            FirebaseAuth.getInstance().currentUser!!.uid, calendar, smsDelivered
        )
        /**
         * insert Message Firebase
         */
        databaseReference.child(smsId).setValue(message)

        /**
         * set Alarm Message
         */
        addMessageViewModel.setSMSAlarm(
            message.getSmsId(), message.getSmsReceiverName(),
            message.getSmsReceiverNumber(), message.getSmsMsg(), message.getSmsDate(),
            message.getSmsTime(), message.getSmsStatus(), message.getSmsType(), message.getUserID(),
            message.getSmsCalender(), message.getSmsDelivered()
        )
    }

    fun scheduleWhatsAppMessageRepository(
        personName: String, receiverNumber: String, SMSMessage: String,
        date: String, time: String, status: String, type: String,
        smsDelivered: String,calendar: Long
    ) {

        /**
         * create  Message Firebase
         */
        databaseReference = FirebaseDatabase.getInstance().getReference("Message")
        val smsId: String = databaseReference.push().key.toString()
        val message = MessageFirebase(
            smsId, personName, receiverNumber, SMSMessage, date, time, status, type,
            FirebaseAuth.getInstance().currentUser!!.uid, calendar, smsDelivered
        )
        /**
         * insert Message Firebase
         */
        databaseReference.child(smsId).setValue(message)

    }

    fun sync() {
        updateList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance().getReference("Message")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                updateList.clear()
                for (completeSnapShot in p0.children) {
                    val completedMessage = completeSnapShot.getValue(MessageFirebase::class.java)
                    if (completedMessage!!.getUserID() == FirebaseAuth.getInstance().currentUser!!.uid) {
                        updateList.add(completedMessage)
                    }
                }
                if (updateList.size > 0) {
                    Toast.makeText(activity, "Synced Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Synced Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                //TODO Result if Have Error on database
            }
        })
    }




}







