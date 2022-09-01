package com.gooduckrefactoring.view

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gooduckrefactoring.R
import com.gooduckrefactoring.viewmodel.CartViewModel
import com.gooduckrefactoring.viewmodel.CartViewModelFactory
import com.gooduckrefactoring.viewmodel.LoginViewModel
import com.gooduckrefactoring.viewmodel.LoginViewModelFactory


abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding : T
    abstract val layoutId : Int

    lateinit var mContext: Context

    lateinit var titleTxt: TextView
    lateinit var cartCnt: TextView

    lateinit var bagBtn: ImageView
    lateinit var backBtn: ImageView
    lateinit var logo: ImageView

    lateinit var background: FrameLayout

    private val viewModel by lazy {
        ViewModelProvider(this, CartViewModelFactory())[CartViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        mContext = this

        viewModel.cartItemList.observe(this){
            cartCnt.text = it.size.toString()
        }

        supportActionBar?.let {
            setCustomActionBar()
        }

    }

    abstract fun setupEvents()
    abstract fun setValues()
    abstract fun initAppbar()

    open fun setCustomActionBar() {

        val defActionBar = supportActionBar!!
        defActionBar.elevation = 0F
        defActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defActionBar.setCustomView(R.layout.custom_action_bar)

        val toolbar = defActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)

        titleTxt = defActionBar.customView.findViewById(R.id.titleTxt)
        cartCnt = defActionBar.customView.findViewById(R.id.cartCnt)
        logo = defActionBar.customView.findViewById(R.id.logo)
        bagBtn = defActionBar.customView.findViewById(R.id.bagBtn)
        backBtn = defActionBar.customView.findViewById(R.id.backBtn)
        background = defActionBar.customView.findViewById(R.id.background)

        bagBtn.setOnClickListener {
//            val myIntent = Intent(mContext, CartActivity::class.java)
//            startActivity(myIntent)
        }

        backBtn.setOnClickListener {
            finish()
        }

    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view: View? = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x: Float = ev.rawX + view.getLeft() - scrcoords[0]
            val y: Float = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }
        return super.dispatchTouchEvent(ev)
    }

// 얘는 포커스도 사라짐
//    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
//        if (event?.action === MotionEvent.ACTION_DOWN) {
//            val v = currentFocus
//            if (v is EditText) {
//                val outRect = Rect()
//                v.getGlobalVisibleRect(outRect)
//                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
//                    v.clearFocus()
//                    val imm: InputMethodManager =
//                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
//                }
//            }
//        }
//        return super.dispatchTouchEvent(event)
//    }

}