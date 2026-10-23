
package id.ac.polbeng.triwahyuningsih.githubprofile.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import id.ac.polbeng.triwahyuningsih.githubprofile.GlideApp
import id.ac.polbeng.triwahyuningsih.githubprofile.R
import id.ac.polbeng.triwahyuningsih.githubprofile.databinding.ActivityMainBinding
import id.ac.polbeng.triwahyuningsih.githubprofile.models.GithubUser
import id.ac.polbeng.triwahyuningsih.githubprofile.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Menghubungkan Activity dengan ViewModel
        mainViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]

        // Mengamati perubahan data profil GitHub
        mainViewModel.githubUser.observe(this) { githubUser ->
            if (githubUser != null) {
                setUserData(githubUser)
            } else {
                binding.tvUser.text = "Profil tidak ditemukan atau gagal dimuat"

                GlideApp.with(this@MainActivity)
                    .load(R.drawable.ic_baseline_broken_image_24)
                    .into(binding.imgUser)
            }
        }

        // Mengamati status loading
        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        // Menangani tombol pencarian
        binding.btnSearchUserLogin.setOnClickListener {
            val userLogin =
                binding.etSearchUserLogin.text.toString().trim()

            if (userLogin.isNotEmpty()) {
                mainViewModel.searchUser(userLogin)
            } else {
                binding.etSearchUserLogin.error =
                    "Username tidak boleh kosong"
            }
        }
    }

    private fun setUserData(githubUser: GithubUser) {
        binding.tvUser.text = githubUser.toString()

        GlideApp.with(this@MainActivity)
            .load(githubUser.avatarUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
            )
            .into(binding.imgUser)

        Log.d(TAG, "Profil pengguna berhasil ditampilkan")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}
