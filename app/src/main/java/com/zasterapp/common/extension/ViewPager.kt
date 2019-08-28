package co.asisten.android.common.extension

import androidx.viewpager.widget.ViewPager

fun ViewPager.doOnPageSelected(block: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            block(position)
        }
    })
}
