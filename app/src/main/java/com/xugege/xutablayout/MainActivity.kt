package com.xugege.xutablayout

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.text.method.Touch
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.xugege.xu_lib_tablayout.tablayout.CommonTabLayout
import com.xugege.xu_lib_tablayout.tablayout.listener.CustomTabEntity
import com.xugege.xu_lib_tablayout.tablayout.listener.OnTabSelectListener
import com.xugege.xutablayout.delegate.BlankFragment
import com.xugege.xutablayout.delegate.TabEntity
import com.xugege.xutablayout.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity(), BlankFragment.OnFragmentInteractionListener {
    val TAG: String = this.javaClass.simpleName
    private val mTitles = arrayOf("首页", "消息", "联系人", "更多")
    private val mFragments = ArrayList<Fragment>()
    private var mContext: Context? = null
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mIconUnselectIds = intArrayOf(
        R.mipmap.tab_home_unselect,
        R.mipmap.tab_speech_unselect,
        R.mipmap.tab_contact_unselect,
        R.mipmap.tab_more_unselect
    )
    private val mIconSelectIds = intArrayOf(
        R.mipmap.tab_home_select,
        R.mipmap.tab_speech_select,
        R.mipmap.tab_contact_select,
        R.mipmap.tab_more_select
    )

    override fun onFragmentInteraction(uri: Uri) {

    }

    internal var mRandom = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this


        for (title in mTitles) {
            mFragments.add(BlankFragment.newInstance("Switch ViewPager $title", ""))
        }


        for (i in mTitles.indices) {
            Log.d(TAG, "" + i + "  " + mTitles[i])
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }

        vp.setAdapter(MyPagerAdapter(supportFragmentManager))
        tb.setTabTouch(object : CommonTabLayout.TabTouch {
            override fun touch(
                v: View?,
                event: MotionEvent?,
                position: Int,
                allTabCount: Int
            ): Boolean {
                if (event?.action == MotionEvent.ACTION_UP){
                    if (position == allTabCount-1) {
                        Log.d(TAG, "ACTION_UP  是最后一个  ")
                        (mContext as MainActivity).startActivity(
                            Intent(
                                mContext,
                                LoginActivity::class.java
                            )
                        )
                        return true
                    }else{
                        Log.d(TAG, " 不是最后一个  ")
                        return false
                    }
                }else{
                    return false
                }


            }


        })

        tb.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabReselect(position: Int) {
                if (position == 0) {
                    tb.showMsg(0, mRandom.nextInt(100) + 1)
                    //                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }

            override fun onTabSelect(position: Int) {
                if (position == mTitles.size - 1) {
                    try {
                        Log.d(TAG, "select " + position)

                    } catch (ex: Exception) {
                        Log.d(TAG, "select error " + ex.message)
                    }

                } else {
                    Log.d(TAG, "start Login")
                    vp.setCurrentItem(position)
                }
            }
        })

        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                tb.setCurrentTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        tb.setTabData(mTabEntities)
    }


    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }
    }

    protected fun dp2px(dp: Float): Int {
        val scale = mContext?.getResources()?.displayMetrics?.density
        return (dp * scale!! + 0.5f).toInt()
    }
}


