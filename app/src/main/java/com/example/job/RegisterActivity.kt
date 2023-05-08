package com.example.job

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.job.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.widget.RxTextView

@SuppressLint("CheckResult")
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth

    var firebaseDatabase: FirebaseDatabase?=null
    var reference : DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase= FirebaseDatabase.getInstance()
        reference=firebaseDatabase!!.getReference("User")

        auth= FirebaseAuth.getInstance(  )

        // fullname validation
        val nameStream = RxTextView.textChanges(binding.etFullname)
            .skipInitialValue()
            .map { name->
                name.isEmpty()
            }
        nameStream.subscribe{
            showNameExistAlert(it)
        }
        //Email Validation

        val emailStream =RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe{
            showEmailValidtAlert(it)
        }

        //username validation
        val usernameSteam = RxTextView.textChanges(binding.etUsername)
            .skipInitialValue()
            .map { username->
                username.length<6
            }
        usernameSteam.subscribe{
            showTextMinimalAlert(it,"Username")
        }

        //password validation
        val passwordSteam = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password->
                password.length<6
            }
        passwordSteam.subscribe{
            showTextMinimalAlert(it,"Password")
        }

        //confirm password validation
        val passwordConfirmStream = io.reactivex.Observable.merge(
            RxTextView.textChanges(binding.etPassword)
                .skipInitialValue()
                .map { password->
                    password.toString() != binding.etComfirmPassword.text.toString()
                },
            RxTextView.textChanges(binding.etComfirmPassword)
                .skipInitialValue()
                .map { confirmPassword->
                    confirmPassword.toString() !=binding.etPassword.text.toString()
                })
        passwordConfirmStream.subscribe{
            showPasswordComfirmAlert(it)
        }

        //button enable true or false
         val invalidFieldsStream = io.reactivex.Observable.combineLatest(
             nameStream,
             emailStream,
             usernameSteam,
             passwordSteam,
             passwordConfirmStream,
             {nameInvalid :Boolean ,emailInvalid :Boolean ,usernameInvalid :Boolean,passwordInvalid :Boolean,passwordConfirmInvalid :Boolean ->
                 !nameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid && !passwordConfirmInvalid
             })

        invalidFieldsStream.subscribe { isValid->
            if(isValid){
                binding.btnRegister.isEnabled=true
                binding.btnRegister.backgroundTintList= ContextCompat.getColorStateList(this,R.color.primary_color)
            } else {
                binding.btnRegister.isEnabled=false
                binding.btnRegister.backgroundTintList= ContextCompat.getColorStateList(this,android.R.color.darker_gray)

            }
        }

//click


        binding.btnRegister.setOnClickListener{


            val database = firebaseDatabase!!.getReference("User")
            val uid = generateUniqueId(database);
            val rename=binding.etFullname.text.toString()
            val reemail=binding.etEmail.text.toString()
            val reusername=binding.etUsername.text.toString()
            val repassword=binding.etPassword.text.toString()

            var User = UserHelperClass(rename,reemail,reusername,repassword,uid)

            reference!!.child(uid).setValue(User)
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            registerUser(email,password)

        }
        binding.tvHaveAccount.setOnClickListener{
            startActivity(Intent(this, LoginActivity ::class.java))
        }
    }

    private fun showNameExistAlert(isNotValid:Boolean){
        binding.etFullname.error = if(isNotValid)"Name cannot be empty!" else null
    }
    private fun showTextMinimalAlert(isNotValid:Boolean,text:String) {
        if (text == "Username")
            binding.etUsername.error = if (isNotValid) "$text The Username must be more than 6 characters!" else null
        else if (text == "Password")
            binding.etPassword.error = if (isNotValid) "$text The password must be at least 6 characters long!" else null
    }
    private fun showEmailValidtAlert(isNotValid:Boolean){
        binding.etEmail.error = if(isNotValid)"Email is not valid!" else null
    }
    private fun showPasswordComfirmAlert(isNotValid:Boolean){
        binding.etComfirmPassword.error = if(isNotValid)"Passwords do not match!" else null
    }

    private fun registerUser(email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    startActivity(Intent(this,LoginActivity::class.java))
                    Toast.makeText(this,"Register berhasli!",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,it.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun generateUniqueId(databaseRef: DatabaseReference): String {
        val newRef = databaseRef.push()
        return newRef.key ?: ""
    }
}