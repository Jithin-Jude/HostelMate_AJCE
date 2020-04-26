package com.ajce.hostelmate.login

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ajce.hostelmate.R
import com.ajce.hostelmate.reportissue.IssueStatusActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_inmates_login.*

class InmatesLoginActivity : AppCompatActivity() {
    var PERSON_NAME: String? = "PERSON_NAME"
    var PERSON_EMAIL: String? = "PERSON_EMAIL"
    var PROFILE_PIC: String? = "PROFILE_PIC"
    var FIRST_TIME_CHECK: String? = "FIRST_TIME_CHECK"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    //var signInButton: SignInButton? = null
    //var signInProgress: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inmates_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        sign_in_progress.setVisibility(View.INVISIBLE)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.setOnClickListener(View.OnClickListener { signIn() })
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
        sign_in_progress.setVisibility(View.VISIBLE)
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
                sign_in_progress.setVisibility(View.INVISIBLE)
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
                        sign_in_progress.setVisibility(View.INVISIBLE)
                        Toast.makeText(applicationContext, R.string.signIn_with_credential_failure, Toast.LENGTH_LONG).show()
                        //updateUI(null);
                    }
                }
    }

    fun successSignIn() {
        sign_in_progress.setVisibility(View.INVISIBLE)
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
        val intent = Intent(applicationContext, IssueStatusActivity::class.java)
        intent.putExtra(PERSON_NAME, personName)
        intent.putExtra(PERSON_EMAIL, personEmail)
        intent.putExtra(PROFILE_PIC, personPhoto.toString())
        startActivity(intent)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
        editor.putString(FIRST_TIME_CHECK, "inmate")
        editor.apply()
        finish()
        Toast.makeText(this, getString(R.string.welcome) + personName, Toast.LENGTH_LONG).show()
    }

    companion object {
        var mGoogleSignInClient: GoogleSignInClient? = null
        private const val RC_SIGN_IN = 1
        private val TAG: String? = "InmatesLoginActivity"
    }
}