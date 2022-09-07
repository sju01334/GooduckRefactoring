package com.gooduckrefactoring.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.FragmentMyGooduckBinding
import com.gooduckrefactoring.view.LoginActivity
import com.gooduckrefactoring.viewmodel.UserViewModel
import com.gooduckrefactoring.viewmodel.UserViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.nepplus.gooduck.utils.ContextUtil
import com.nepplus.gooduck.utils.GlobalData

class MyGooduckFragment : BaseFragment<FragmentMyGooduckBinding>() {

    override val layoutId: Int = R.layout.fragment_my_gooduck

    lateinit var mGoogleSignInClient : GoogleSignInClient

    private val userViewModel by lazy {
        ViewModelProvider(this, UserViewModelFactory(requireActivity().application))[UserViewModel::class.java]
    }

    override fun init() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        mGoogleSignInClient = this.let { GoogleSignIn.getClient(requireContext(), gso) }

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.logoutBtn.setOnClickListener {
            userViewModel.logout(mGoogleSignInClient)
        }

    }

    override fun setValues() {

        userViewModel.isLogout.observe(viewLifecycleOwner){
            if(it){
                logout()
            }
        }


    }


    fun logout(){
        ContextUtil.clear(requireContext())
        GlobalData.loginUser = null
        val myIntent = Intent(requireContext(), LoginActivity::class.java)
        myIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(myIntent)
    }


}