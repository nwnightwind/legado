package io.legado.app.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.legado.app.R
import io.legado.app.base.BaseActivity
import io.legado.app.help.permission.Permissions
import io.legado.app.help.permission.PermissionsCompat
import io.legado.app.help.storage.Restore
import io.legado.app.ui.main.bookshelf.BookshelfFragment
import io.legado.app.ui.main.booksource.BookSourceFragment
import io.legado.app.ui.main.findbook.FindBookFragment
import io.legado.app.ui.main.myconfig.MyConfigFragment
import io.legado.app.utils.getViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>(), BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {

    override val viewModel: MainViewModel
        get() = getViewModel(MainViewModel::class.java)

    override val layoutID: Int
        get() = R.layout.activity_main

    override fun onViewModelCreated(viewModel: MainViewModel, savedInstanceState: Bundle?) {
        view_pager_main.offscreenPageLimit = 3
        view_pager_main.adapter = TabFragmentPageAdapter(supportFragmentManager)
        view_pager_main.addOnPageChangeListener(this)
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_bookshelf -> view_pager_main.currentItem = 0
            R.id.menu_find_book -> view_pager_main.currentItem = 1
            R.id.menu_book_source -> view_pager_main.currentItem = 2
            R.id.menu_my_config -> view_pager_main.currentItem = 3
        }
        return false
    }

    override fun onCompatCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCompatCreateOptionsMenu(menu)
    }

    override fun onCompatOptionsItemSelected(item: MenuItem): Boolean {
        return super.onCompatOptionsItemSelected(item)
    }

    private fun importYueDu() {
        PermissionsCompat.Builder(this)
            .addPermissions(*Permissions.Group.STORAGE)
            .rationale(R.string.tip_perm_request_storage)
            .onGranted { Restore.importYueDuData(this) }.request()
    }

    override fun onPageScrollStateChanged(state: Int) {

    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }


    override fun onPageSelected(position: Int) {
        bottom_navigation_view.menu.getItem(position).isChecked = true
    }

    private inner class TabFragmentPageAdapter internal constructor(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> BookshelfFragment()
                1 -> FindBookFragment()
                2 -> BookSourceFragment()
                else -> MyConfigFragment()
            }
        }

        override fun getCount(): Int {
            return 4
        }
    }

}
