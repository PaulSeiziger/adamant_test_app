package im.adamant.android_test_app.core.extentions

import android.animation.Animator
import android.view.View

fun View.animateHide(duration: Long) {
    animate().alpha(0.0f).setDuration(duration).setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            visibility = View.INVISIBLE
            //alpha = 1.0f
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationStart(animation: Animator?) {

        }
    })
}

fun View.animateShow(duration: Long) {
    animate().alpha(1f).setDuration(duration).setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {

        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationStart(animation: Animator?) {
            visibility = View.VISIBLE
        }
    })
}