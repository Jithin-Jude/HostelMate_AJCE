package com.ajce.hostelmate.login

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ajce.hostelmate.R
import com.ajce.hostelmate.reportissue.InmatesDashboardActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_inmates_login.*

class InmatesLoginActivity : AppCompatActivity() {
    var USER_NAME: String? = "user_name"
    var USER_EMAIL: String? = "user_email"
    var PROFILE_PIC: String? = "profile_pic"
    var FIRST_TIME_CHECK: String? = "first_time_check"

    var INMATE: String = "inmate"
    var RECEPTIONIST: String = "receptionist"

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    //var signInButton: SignInButton? = null
    //var signInProgress: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inmates_login)

        supportActionBar?.hide()

//        var utils = Utils()

        swapUserMode.setOnClickListener {
            val intent = Intent(applicationContext, ReceptionLoginActivity::class.java)
            intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
/*            if (INMATE.equals(utils.currentUserMode)){
                utils.currentUserMode = RECEPTIONIST
                val intent = Intent(applicationContext, ReceptionLoginActivity::class.java)
                startActivity(intent)
            } else {
                utils.currentUserMode = INMATE
                val intent = Intent(applicationContext, InmatesLoginActivity::class.java)
                startActivity(intent)
            }*/
        }

        mAuth = FirebaseAuth.getInstance()

        signInProgress.visibility = View.INVISIBLE
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInButton.setOnClickListener(View.OnClickListener { signIn() })
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.currentUser
        if (currentUser != null) {
            successSignIn()
        }
        //updateUI(currentUser);
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
        signInProgress.setVisibility(View.VISIBLE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                signInProgress.setVisibility(View.INVISIBLE)
                Toast.makeText(this, R.string.google_sign_in_failed, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct?.getId())
        val credential = GoogleAuthProvider.getCredential(acct?.getIdToken(), null)
        mAuth?.signInWithCredential(credential)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Toast.makeText(getApplicationContext(), "signInWithCredential:success", Toast.LENGTH_LONG).show();
                        val user = mAuth?.getCurrentUser()
                        successSignIn()
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        signInProgress.setVisibility(View.INVISIBLE)
                        Toast.makeText(applicationContext, R.string.signIn_with_credential_failure, Toast.LENGTH_LONG).show()
                        //updateUI(null);
                    }
                }
    }

    fun successSignIn() {
        signInProgress.visibility = View.INVISIBLE
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        var personName: String? = null
        var personGivenName: String? = null
        var personFamilyName: String? = null
        var personEmail: String? = null
        var personId: String? = null
        var personPhoto: Uri? = null
        if (acct != null) {
            personName = acct.displayName
            personGivenName = acct.givenName
            personFamilyName = acct.familyName
            personEmail = acct.email
            personId = acct.id
            personPhoto = acct.photoUrl
        }
        val intent = Intent(applicationContext, InmatesDashboardActivity::class.java)
        intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(USER_NAME, personName)
        intent.putExtra(USER_EMAIL, personEmail)
        intent.putExtra(PROFILE_PIC, personPhoto.toString())
        startActivity(intent)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
        editor.putString(FIRST_TIME_CHECK, "inmate")
        editor.apply()
        finish()
        Toast.makeText(this, getString(R.string.welcome) + " " + personName, Toast.LENGTH_LONG).show()
    }

    companion object {
        var mGoogleSignInClient: GoogleSignInClient? = null
        private const val RC_SIGN_IN = 1
        private val TAG: String? = "InmatesLoginActivity"
    }
}